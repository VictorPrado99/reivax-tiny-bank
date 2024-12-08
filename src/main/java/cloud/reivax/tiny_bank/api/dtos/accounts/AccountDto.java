package cloud.reivax.tiny_bank.api.dtos.accounts;

import cloud.reivax.tiny_bank.api.dtos.users.UserDto;

import java.util.List;
import java.util.UUID;

public record AccountDto(
        UUID accountId,
        UserDto user,
        List<TransactionDto> transactionHistory
) {
}
