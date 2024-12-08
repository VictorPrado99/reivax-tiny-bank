package cloud.reivax.tiny_bank.services.models.accounts;

import cloud.reivax.tiny_bank.services.processors.TransactionType;

import java.util.UUID;

public record TransactionModel(
        TransactionType transactionType,
        UUID transactionId,
        String origin,
        String recipient,
        Double amount
) {
}
