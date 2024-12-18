package cloud.reivax.tiny_bank.api.controllers.impl;

import cloud.reivax.tiny_bank.api.controllers.AccountsController;
import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.CreateTransactionDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.TransactionDto;
import cloud.reivax.tiny_bank.services.AccountService;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.UuidUtility;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import cloud.reivax.tiny_bank.utils.mappers.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AccountsControllerImpl implements AccountsController {

    private final AccountService accountService;

    @Override
    public ResponseEntity<List<AccountDto>> getAccounts(String accountsIds) {
        String[] accountIdsArray = accountsIds != null ? accountsIds.split(",") : null;

        List<AccountModel> accountModels = accountService.getAccounts(accountIdsArray);

        List<AccountDto> accounts = accountModels.stream()
                .map(AccountMapper.INSTANCE::modelToDto)
                .toList();

        return ResponseEntity.ok(accounts);
    }

    @Override
    public ResponseEntity<Void> transactionExchange(CreateTransactionDto createTransactionDto) {
        TransactionModel transactionModel = TransactionMapper.INSTANCE.dtoToTransactionModel(createTransactionDto);

        accountService.executeTransaction(transactionModel);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AccountDto> retrieveAccountBalance(String accountId) {
        UUID parsedAccountId = UuidUtility.parseUUIDString(accountId);
        if (parsedAccountId == null) {
            ExceptionThrower.throwUuidNotComplain();
        }

        AccountDto accountDto = AccountMapper.INSTANCE.modelToDto(accountService.retrieveAccount(parsedAccountId));

        return ResponseEntity.ok(accountDto);
    }

    @Override
    public ResponseEntity<List<TransactionDto>> retrieveAccountTransactionHistory(String accountId) {
        UUID parsedAccountId = UuidUtility.parseUUIDString(accountId);
        if (parsedAccountId == null) {
            ExceptionThrower.throwUuidNotComplain();
        }

        List<TransactionModel> transactionModels = accountService.retrieveTransactionHistory(parsedAccountId);

        List<TransactionDto> transactionDtos = transactionModels.stream()
                .map(TransactionMapper.INSTANCE::modelToDto)
                .toList();

        return ResponseEntity.ok(transactionDtos);
    }
}
