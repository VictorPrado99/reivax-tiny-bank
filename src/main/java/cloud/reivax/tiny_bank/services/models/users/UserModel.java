package cloud.reivax.tiny_bank.services.models.users;

import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public record UserModel(
        UUID userId,
        String userName,
        boolean isUserEnabled,
        Map<UUID, AccountModel> accounts

) {

}
