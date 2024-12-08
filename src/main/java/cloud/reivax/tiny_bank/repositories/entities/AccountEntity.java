package cloud.reivax.tiny_bank.repositories.entities;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record AccountEntity(
        UUID accountId,
        UUID userId,
        // I am assuming single currency.
        Double balance,

        List<TransactionEntity> transactionHistory
) {
}
