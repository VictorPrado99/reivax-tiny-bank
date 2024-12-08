package cloud.reivax.tiny_bank.services.models.users;

import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record UserModel(
        UUID userId,
        String userName,
        boolean isUserEnabled,
        List<AccountModel> accounts

) {
    public void addAccount(AccountModel account) {
        accounts.add(account);
    }
}
