package cloud.reivax.tiny_bank.utility;

import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public final class DbHelperTester {

    public static UserEntity createUserInDb(UUID userId, UUID accountId, String userName, Map<UUID, UserEntity> db) {
        LinkedList<TransactionEntity> transactionHistory = new LinkedList<>();
        UUID transactionId = UUID.randomUUID();
        UUID origin = UUID.randomUUID();
        UUID recipient = UUID.randomUUID();
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionId(transactionId)
                .origin(origin)
                .recipient(recipient)
                .amount(100.52)
                .transactionType("DEPOSIT")
                .build();

        transactionHistory.add(transactionEntity);

        AccountEntity accountEntity = AccountEntity.builder()
                .accountId(accountId)
                .userId(userId)
                .balance(100.52)
                .transactionHistory(transactionHistory)
                .build();

        HashMap<UUID, AccountEntity> accounts = new HashMap<>();
        accounts.put(accountId, accountEntity);

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .isUserEnabled(true)
                .userName(userName)
                .accounts(accounts)
                .build();

        db.put(userId, userEntity);
        return userEntity;
    }

}
