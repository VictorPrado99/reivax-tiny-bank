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

@AllArgsConstructor
@Component
public class TransferTransactionProcessor implements TransactionProcessor {
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;

    @Override
    public void process(TransactionModel transaction) {
        String supposedOriginAccountId = transaction.origin();
        String supposedRecipientAccountId = transaction.recipient();
        UUID originAccountId = null;
        UUID recipientAccountId = null;
        try {
            originAccountId = UUID.fromString(supposedOriginAccountId);
            recipientAccountId = UUID.fromString(supposedRecipientAccountId);
        } catch (Exception e) {
            ExceptionThrower.throw406("AccountId Not Valid");
        }

        AccountEntity originAccountEntity = accountRepository.findAccount(originAccountId);
        AccountEntity recipientAccountEntity = accountRepository.findAccount(recipientAccountId);

        if (originAccountEntity == null || recipientAccountEntity == null) {
            ExceptionThrower.throw404("Recipient or origin account not found");
        }

        AccountModel originAccount = accountMapper.entityToModel(originAccountEntity);
        AccountModel recipientAccount = accountMapper.entityToModel(recipientAccountEntity);

        transaction = transactionMapper.entityToModel(
                accountRepository.generateTransactionEntity(transactionMapper.modelToEntity(transaction)));

        if (originAccount.getBalance() < transaction.amount()) {
            ExceptionThrower.throw422("Not enough balance for this Transfer");
        }

        originAccount.processTransaction(transaction, OperationType.ADD);
        recipientAccount.processTransaction(transaction, OperationType.ADD);

        accountRepository.save(accountMapper.modelToEntity(originAccount));
        accountRepository.save(accountMapper.modelToEntity(recipientAccount));
    }
}
