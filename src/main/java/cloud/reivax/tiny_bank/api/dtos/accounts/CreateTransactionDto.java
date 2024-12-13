package cloud.reivax.tiny_bank.api.dtos.accounts;

import cloud.reivax.tiny_bank.services.processors.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateTransactionDto(
        @Schema(enumAsRef = true, implementation = TransactionType.class, defaultValue = "TRANSFER")
        String transactionType,
        @Schema(defaultValue = "28de0db2-2541-4b50-97e1-672fb5d386a1")
        @Pattern(message = "It is not an UUID", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        UUID origin,

        @Schema(defaultValue = "9130fbf3-ef2e-46ee-ac21-c49a99b6d13b")
        @Pattern(message = "It is not an UUID", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        UUID recipient,
        @Min(value = 0, message = "Negative Number not Allowed")
        @DecimalMin(value = "0.01", message = "Must have some amount")
        @Schema(defaultValue = "24.42")
        Double amount
) {
}
