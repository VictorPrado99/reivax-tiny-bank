package cloud.reivax.tiny_bank.services.processors.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.OperationType;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.processors.TransactionProcessor;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
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

        AccountMapper mapperInstance = AccountMapper.INSTANCE;

        AccountEntity account = accountRepository.findAccount(originAccountId);

        if (account == null) {
            ExceptionThrower.throw404("Account to withdraw not found");
        }

        AccountModel recipientAccount = mapperInstance.entityToModel(account);

        recipientAccount.processTransaction(transaction, OperationType.SUBTRACT);

        accountRepository.save(mapperInstance.modelToEntity(recipientAccount));
    }
}
