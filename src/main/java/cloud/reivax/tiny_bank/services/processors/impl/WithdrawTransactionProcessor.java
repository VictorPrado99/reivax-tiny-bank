package cloud.reivax.tiny_bank.services.processors.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.OperationType;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.processors.TransactionProcessor;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import cloud.reivax.tiny_bank.utils.mappers.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class WithdrawTransactionProcessor implements TransactionProcessor {

    private final AccountRepository accountRepository;

    @Override
    public void process(TransactionModel transaction) {
        String supposedOriginAccountId = transaction.origin();
        UUID originAccountId = null;
        try {
            originAccountId = UUID.fromString(supposedOriginAccountId);
        } catch (Exception e) {
            ExceptionThrower.throw406("Origin AccountId Not Valid");
        }

        AccountMapper accountMapper = AccountMapper.INSTANCE;

        AccountEntity account = accountRepository.findAccount(originAccountId);

        if (account == null) {
            ExceptionThrower.throw404("Account to withdraw not found");
        }

        AccountModel recipientAccount = accountMapper.entityToModel(account);

        TransactionMapper transactionMapper = TransactionMapper.INSTANCE;
        transaction = transactionMapper.entityToModel(
                accountRepository.generateTransactionEntity(transactionMapper.modelToEntity(transaction)));

        if (recipientAccount.getBalance() < transaction.amount()) {
            ExceptionThrower.throw422("Not enough balance for this Withdraw");
        }
        recipientAccount.processTransaction(transaction, OperationType.SUBTRACT);

        accountRepository.save(accountMapper.modelToEntity(recipientAccount));
    }
}
