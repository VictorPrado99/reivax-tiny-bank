package cloud.reivax.tiny_bank.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.UUID;

@UtilityClass
public final class InMemoryDbUtility {

    public UUID getOrGenerateKey(UUID key, Set<UUID> dbKeys) {
        return key != null ? key : generateUniqueKey(dbKeys);
    }

    private UUID generateUniqueKey(Set<UUID> dbKeys) {
        UUID key;
        do {
            key = UUID.randomUUID();
        } while (dbKeys.contains(key));

        return key;
    }

}
