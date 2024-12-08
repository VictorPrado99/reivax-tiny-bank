package cloud.reivax.tiny_bank.repositories.entities;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class UserEntity {
    private final UUID userId;
    private final String userName;

    @Builder.Default
    private boolean isUserEnabled = true;

    @Builder.Default
    private final List<AccountEntity> accounts = new ArrayList<>();

    public void addAccount(AccountEntity account) {
        accounts.add(account);
    }

}
