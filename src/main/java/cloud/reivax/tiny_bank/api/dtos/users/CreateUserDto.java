package cloud.reivax.tiny_bank.api.dtos.users;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserDto(
        // For now, I will just have a userName for our User
        @NotEmpty
        String userName
) {

}
