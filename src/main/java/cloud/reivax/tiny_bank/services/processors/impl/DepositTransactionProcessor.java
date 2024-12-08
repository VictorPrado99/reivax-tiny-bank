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
public class DepositTransactionProcessor implements TransactionProcessor {

    private final AccountRepository accountRepository;

    @Override
    public void process(TransactionModel transaction) {
        String supposedRecipientAccountId = transaction.recipient();
        UUID recipientAccountId = null;
        try {
            recipientAccountId = UUID.fromString(supposedRecipientAccountId);
        } catch (Exception e) {
            ExceptionThrower.throw406("Recipient AccountId Not Valid");
        }

        AccountMapper mapperInstance = AccountMapper.INSTANCE;

        AccountEntity account = accountRepository.findAccount(recipientAccountId);

        if (account == null) {
            ExceptionThrower.throw404("Recipient account not found");
        }

        AccountModel recipientAccount = mapperInstance.entityToModel(account);
        TransactionMapper transactionMapper = TransactionMapper.INSTANCE;
        transaction = transactionMapper.entityToModel(
                accountRepository.generateTransactionEntity(transactionMapper.modelToEntity(transaction)));

        recipientAccount.processTransaction(transaction, OperationType.ADD);

        accountRepository.save(mapperInstance.modelToEntity(recipientAccount));
    }
}
