package cloud.reivax.tiny_bank.repositories.entities;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserEntity(
        UUID userId,
        String userName
) {
}
