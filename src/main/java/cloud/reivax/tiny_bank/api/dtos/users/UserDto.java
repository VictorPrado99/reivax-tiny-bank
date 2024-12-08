package cloud.reivax.tiny_bank.api.dtos.users;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record UserDto(
        UUID userId,
        String userName,
        Map<UUID, AccountDto> accounts
) {
}
