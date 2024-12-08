package cloud.reivax.tiny_bank.services.models;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserModel(
        UUID userId,
        String userName
) {
}
