package cloud.reivax.tiny_bank.repositories.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.utils.InMemoryDbUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class InMemoryAccountRepositoryImpl implements AccountRepository {

    private final Map<UUID, UserEntity> db;

    @Override
    public AccountEntity save(AccountEntity accountEntity) {
        UUID accountId = InMemoryDbUtility.getOrGenerateKey(accountEntity.accountId(), db.keySet());
        UUID userId = accountEntity.userId();
        AccountEntity toSaveEntity = accountEntity.toBuilder()
                .accountId(accountId)
                .build();

        UserEntity userEntity = db.get(userId);
        userEntity.addAccount(toSaveEntity);

        return toSaveEntity;
    }

    @Override
    public AccountEntity findAccount(UUID accountId) {
        //In this one chose to do different from User, instead of throwing an error,
        //I made it return null to be filtered at service layer. This is just to showcase,
        //in a real app wouldn't be good creating two behaviors to similar methods
        return db.values().stream()
                .flatMap(userEntity -> userEntity.getAccounts().stream())
                .filter(accountEntity -> accountEntity.accountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public TransactionEntity findTransaction(UUID transactionId) {
        return db.values().stream()
                .flatMap(userEntity -> userEntity.getAccounts().stream())
                .flatMap(accountEntity -> accountEntity.transactionHistory().stream())
                .filter(transactionEntities -> transactionEntities.transactionId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AccountEntity> findAllAccounts() {
        return db.values().stream().flatMap(userEntity -> userEntity.getAccounts().stream()).toList();
    }

    @Override
    public UUID getAllowedUuid() {
        Set<UUID> accountKeySet = db.values().stream()
                .flatMap(userEntity -> userEntity.getAccounts().stream())
                .map(AccountEntity::accountId)
                .collect(Collectors.toSet());

        return InMemoryDbUtility.getOrGenerateKey(null, accountKeySet);
    }
}
