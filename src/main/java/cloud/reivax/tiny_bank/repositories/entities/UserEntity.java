package cloud.reivax.tiny_bank.repositories.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class UserEntity {
    private final UUID userId;
    private final String userName;
    private boolean isUserEnabled;
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Map<UUID, AccountEntity> accounts = new HashMap<>();

    public Map<UUID, AccountEntity> getAccounts() {
        if (accounts == null) accounts = new HashMap<>();
        return accounts;
    }

    public void addAccount(AccountEntity account) {
        if (accounts == null) accounts = new HashMap<>();
        accounts.put(account.accountId(), account);
    }

}
