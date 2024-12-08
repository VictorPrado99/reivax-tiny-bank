package cloud.reivax.tiny_bank.api.dtos.users;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record UserDto(
        @Schema(defaultValue = "28de0db2-2541-4b50-97e1-672fb5d386a1")
        UUID userId,
        @Schema(defaultValue = "Reivax")
        String userName,
        Map<UUID, AccountDto> accounts
) {
}
