package cloud.reivax.tiny_bank.repositories.entities;

import java.util.List;
import java.util.UUID;

public record AccountEntity(
        UUID accountId,
        UserEntity userId,
        // I am assuming single currency.
        Double balance,

        List<TransactionEntity> transactionHistory
) {
}
