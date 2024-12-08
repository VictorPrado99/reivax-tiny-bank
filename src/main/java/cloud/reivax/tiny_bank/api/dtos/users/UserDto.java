package cloud.reivax.tiny_bank.api.dtos.users;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(
        UUID userId,
        String userName
) {
}
