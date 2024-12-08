package cloud.reivax.tiny_bank.repositories.entities;

import java.util.UUID;

public record TransactionEntity(
        UUID transactionId,
        String transactionType,
        UUID origin,
        UUID recipient,
        Double amount
) {
}
