package cloud.reivax.tiny_bank.api.dtos.accounts;

import cloud.reivax.tiny_bank.services.processors.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public record TransactionDto(
        @Schema(enumAsRef = true, implementation = TransactionType.class)
        String transactionType,
        @Nullable
        String origin,
        @Nullable
        String recipient,
        @Min(value = 0, message = "Negative Number not Allowed")
        @DecimalMin(value = "0.01", message = "Must have some amount")
        Double amount
) {
}
