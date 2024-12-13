package cloud.reivax.tiny_bank.api.controllers.impl;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.CreateTransactionDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.TransactionDto;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.utility.DbHelperTester;
import cloud.reivax.tiny_bank.utility.ExceptionHelperTester;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AccountsControllerImplTest {

    //I am doing mostly just happy path, so you may notice that we don't have all scenarios covered.
    //Besides that, we don't really have unit tests.

    @Autowired
    Map<UUID, UserEntity> db;

    @Autowired
    AccountsControllerImpl accountsController;

    @Test
    void testSuccessGettingAccount() {
        //Given
        //We have a user at the database
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String dummyUser = "dummyUser";

        UserEntity userInDbEntity = DbHelperTester.createUserInDb(userId, accountId, dummyUser, db);
        AccountMapper accountMapper = AccountMapper.INSTANCE;

        List<AccountDto> accountDtosInDb = userInDbEntity.getAccounts().values().stream()
                .map(accountMapper::entityToModel)
                .map(accountMapper::modelToDto)
                .toList();

        //When
        ResponseEntity<List<AccountDto>> responseEntity = accountsController.getAccounts(accountId.toString());
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        List<AccountDto> body = responseEntity.getBody();

        //Then
        assertEquals(HttpStatus.OK, statusCode);
        assertThat(accountDtosInDb).hasSameElementsAs(body);
    }

    @Test
    void testRetrieveAccountBalance() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "Bara";

        UserEntity userInDbEntity = DbHelperTester.createUserInDb(userId, accountId, userName, db);
        AccountEntity accountEntity = userInDbEntity.getAccounts().get(accountId);

        AccountMapper accountMapper = AccountMapper.INSTANCE;
        AccountDto expectedAccountDto = accountMapper.modelToDto(accountMapper.entityToModel(accountEntity));

        //When
        ResponseEntity<AccountDto> responseEntity = accountsController.retrieveAccountBalance(accountId.toString());
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        AccountDto body = responseEntity.getBody();

        //Then
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(expectedAccountDto, body);
    }

    @Test
    void testFailedToRetrieveNonexistentBalance() {
        ExceptionHelperTester.validateExceptionThrown(accountsController::retrieveAccountBalance, HttpStatus.NOT_FOUND);
    }

    @Test
    void testFailedToRetrieveBalanceFailedId() {
        ExceptionHelperTester.validateExceptionThrown(accountsController::retrieveAccountBalance, HttpStatus.NOT_ACCEPTABLE, "Potato");
    }

    @Test
    void testRetrieveTransactionHistory() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "Bara";

        UserEntity userInDbEntity = DbHelperTester.createUserInDb(userId, accountId, userName, db);
        AccountEntity accountEntity = userInDbEntity.getAccounts().get(accountId);

        AccountMapper accountMapper = AccountMapper.INSTANCE;
        AccountDto expectedAccountDto = accountMapper.modelToDto(accountMapper.entityToModel(accountEntity));
        List<TransactionDto> expectedHistory = expectedAccountDto.transactionHistory();

        //When
        ResponseEntity<List<TransactionDto>> responseEntity = accountsController.retrieveAccountTransactionHistory(accountId.toString());
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        List<TransactionDto> body = responseEntity.getBody();

        //Then
        assertEquals(HttpStatus.OK, statusCode);
        assertThat(expectedHistory).hasSameElementsAs(body);
    }

    @Test
    void testFailedToRetrieveNonexistentTransactionHistory() {
        ExceptionHelperTester.validateExceptionThrown(accountsController::retrieveAccountTransactionHistory, HttpStatus.NOT_FOUND);
    }

    @Test
    void testFailedToRetrieveTransactionHistoryFailedId() {
        ExceptionHelperTester.validateExceptionThrown(accountsController::retrieveAccountTransactionHistory, HttpStatus.NOT_ACCEPTABLE, "Potato");
    }

    @Test
    void testSuccessDepositTransaction() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "HappyPerson";
        UserEntity userInDbEntity = DbHelperTester.createUserInDb(userId, accountId, userName, db);
        UUID origin = UUID.randomUUID();

        double balanceBeforeTransaction = userInDbEntity.getAccounts().get(accountId).balance();

        double transactionAmount = 143.43D;
        CreateTransactionDto depositTransaction = new CreateTransactionDto("DEPOSIT", origin, accountId, transactionAmount);

        //When
        ResponseEntity<Void> responseEntity = accountsController.transactionExchange(depositTransaction);
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        //Then
        List<TransactionEntity> transactionEntities = userInDbEntity.getAccounts().get(accountId).transactionHistory();

        assertEquals(balanceBeforeTransaction + transactionAmount, userInDbEntity.getAccounts().get(accountId).balance());
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(2, transactionEntities.size()); //1 because we had by default, +1 with the new transaction
    }

    @Test
    void testSuccessWithdrawTransaction() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "SadPerson";
        UserEntity userInDbEntity = DbHelperTester.createUserInDb(userId, accountId, userName, db);
        UUID recipient = UUID.randomUUID();

        double balanceBeforeTransaction = userInDbEntity.getAccounts().get(accountId).balance();
        double transactionAmount = 1D;

        CreateTransactionDto withdrawTransaction = new CreateTransactionDto("WITHDRAW", accountId, recipient, transactionAmount);

        //When
        ResponseEntity<Void> responseEntity = accountsController.transactionExchange(withdrawTransaction);
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        //Then
        List<TransactionEntity> transactionEntities = userInDbEntity.getAccounts().get(accountId).transactionHistory();

        assertEquals(balanceBeforeTransaction - transactionAmount, userInDbEntity.getAccounts().get(accountId).balance());
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(2, transactionEntities.size()); //1 because we had by default, +1 with the new transaction
    }

    @Test
    void testSuccessTransferTransaction() {
        //Given
        UUID userId1 = UUID.randomUUID();
        UUID accountId1 = UUID.randomUUID();
        String userName1 = "HappyPerson";
        UserEntity userInDbEntity1 = DbHelperTester.createUserInDb(userId1, accountId1, userName1, db);
        double user1BalanceBefore = userInDbEntity1.getAccounts().get(accountId1).balance();

        UUID userId2 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();
        String userName2 = "HappyPerson";
        UserEntity userInDbEntity2 = DbHelperTester.createUserInDb(userId2, accountId2, userName2, db);
        double user2BalanceBefore = userInDbEntity2.getAccounts().get(accountId2).balance();

        double transferAmount = 1.43D;
        CreateTransactionDto depositTransaction = new CreateTransactionDto("TRANSFER",
                accountId1,
                accountId2,
                transferAmount
        );

        //When
        ResponseEntity<Void> responseEntity = accountsController.transactionExchange(depositTransaction);
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        //Then
        List<TransactionEntity> transactionEntities = userInDbEntity2.getAccounts().get(accountId2).transactionHistory();

        assertEquals(user1BalanceBefore - transferAmount, userInDbEntity1.getAccounts().get(accountId1).balance());
        assertEquals(user2BalanceBefore + transferAmount, userInDbEntity2.getAccounts().get(accountId2).balance());
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(2, transactionEntities.size()); //1 because we had by default, +1 with the new transaction
    }
}
