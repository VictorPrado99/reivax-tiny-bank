package cloud.reivax.tiny_bank.api.dtos.accounts;

import java.util.List;
import java.util.UUID;

public record AccountDto(
        UUID accountId,
        UUID userId,
        double balance,
        List<TransactionDto> transactionHistory
) {
}
