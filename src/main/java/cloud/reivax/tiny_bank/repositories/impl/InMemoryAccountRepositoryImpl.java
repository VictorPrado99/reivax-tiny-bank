package cloud.reivax.tiny_bank.repositories.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import cloud.reivax.tiny_bank.utils.InMemoryDbUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class InMemoryAccountRepositoryImpl implements AccountRepository {

    private final Map<UUID, AccountEntity> accountDb;

    @Override
    public AccountEntity save(AccountEntity accountEntity) {
        UUID accountId = InMemoryDbUtility.getOrGenerateKey(accountEntity.accountId(), accountDb.keySet());

        AccountEntity savedEntity = accountEntity.toBuilder()
                .accountId(accountId)
                .build();

        accountDb.put(accountId, savedEntity);

        return savedEntity;
    }

    @Override
    public AccountEntity findAccount(UUID accountId) {
        //In this one chose to do different from User, instead of throwing an error,
        //I made it return null to be filtered at service layer. This is just to showcase,
        //in a real app wouldn't be good creating two behaviors to similar methods
        return accountDb.get(accountId);
    }

    @Override
    public TransactionEntity findTransaction(UUID transactionId) {
        return accountDb.values().stream()
                .flatMap(accountEntity -> accountEntity.transactionHistory().stream())
                .filter(transactionEntities -> transactionEntities.transactionId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AccountEntity> findAllAccounts() {
        //
        return accountDb.values().stream().toList();
    }
}
