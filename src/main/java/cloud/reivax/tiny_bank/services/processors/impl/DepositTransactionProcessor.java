package cloud.reivax.tiny_bank.services.processors.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
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
public class DepositTransactionProcessor implements TransactionProcessor {

    private final AccountRepository accountRepository;

    @Override
    public void process(TransactionModel transaction) {
        String supposedAccountId = transaction.recipient();
        UUID recipientAccountId = null;
        try {
            recipientAccountId = UUID.fromString(supposedAccountId);
        } catch (Exception e) {
            ExceptionThrower.throw406("Recipient AccountId Not Valid");
        }

        AccountMapper mapperInstance = AccountMapper.INSTANCE;

        AccountModel recipientAccount = mapperInstance.entityToModel(accountRepository.getAccount(recipientAccountId));

        recipientAccount.processTransaction(transaction, OperationType.ADD);

        accountRepository.save(mapperInstance.modelToEntity(recipientAccount));
    }
}
