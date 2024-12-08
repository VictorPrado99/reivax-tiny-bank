package cloud.reivax.tiny_bank.repositories.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    @Override
    public UUID save(AccountEntity accountEntity) {
        return null;
    }

    @Override
    public AccountEntity getAccount(UUID accountId) {
        return null;
    }

    @Override
    public TransactionEntity getTransaction(UUID transactionId) {
        return null;
    }
}
