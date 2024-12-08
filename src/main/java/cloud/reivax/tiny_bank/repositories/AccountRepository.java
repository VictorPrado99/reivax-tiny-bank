package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;

import java.util.UUID;

public interface AccountRepository {

    UUID save(AccountEntity accountEntity);

    AccountEntity getAccount(UUID accountId);

    TransactionEntity getTransaction(UUID transactionId);
}
