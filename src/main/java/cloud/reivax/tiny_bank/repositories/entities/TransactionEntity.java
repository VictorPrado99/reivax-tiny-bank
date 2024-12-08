package cloud.reivax.tiny_bank.repositories.entities;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record TransactionEntity(
        UUID transactionId,
        String transactionType,
        UUID origin,
        UUID recipient,
        Double amount
) {
}
