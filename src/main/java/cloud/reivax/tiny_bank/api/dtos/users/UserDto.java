package cloud.reivax.tiny_bank.api.dtos.users;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserDto(
        UUID userId,
        String userName,
        List<AccountDto> accounts
) {
}
