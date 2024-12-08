package cloud.reivax.tiny_bank.api.dtos.accounts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record AccountDto(
        @Schema(defaultValue = "9130fbf3-ef2e-46ee-ac21-c49a99b6d13b")
        UUID accountId,
        @Schema(defaultValue = "28de0db2-2541-4b50-97e1-672fb5d386a1")
        UUID userId,
        @Schema(defaultValue = "24.42")
        double balance,
        List<TransactionDto> transactionHistory
) {
}
