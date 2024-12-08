package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class InMemoryUserRepositoryImpl implements UserRepository {

    private final Map<UUID, UserEntity> db;
    private final Map<UUID, UserEntity> disabledDb;

    @Override
    public UserEntity findById(UUID userId) {
        if (db.containsKey(userId)) {
            return db.get(userId);
        } else {
            //I choose to throw exception here, because I implemented the "storage", but usually that would probably be at Service Layer
            HttpStatus notFound = HttpStatus.NOT_FOUND;
            throw HttpClientErrorException
                    .create("User not found",
                            notFound,
                            notFound.getReasonPhrase(),
                            null,
                            null,
                            StandardCharsets.UTF_8);
        }
    }

    @Override
    public UUID save(UserEntity userEntity) {
        // I am not worrying about updating user, but theoretically this same method could be used for it,
        // but, we may want further logic to keep historical data or something like that.
        UUID userId = getOrGenerateUserId(userEntity);
        db.put(userId, userEntity.toBuilder()
                .userId(userId)
                .build());
        return userId;
    }

    private UUID getOrGenerateUserId(UserEntity userEntity) {
        return userEntity.userId() != null ? userEntity.userId() : generateUniqueUserId();
    }

    private UUID generateUniqueUserId() {
        UUID userId;
        do {
            userId = UUID.randomUUID();
        } while (db.containsKey(userId));

        return userId;
    }

    @Override
    public Boolean disableById(UUID userId) {
        return transferBetweenDatabases(userId);
    }

    //For in-memory, I am doing that way, with an entire database, be it SQL or No-SQl, would looks different
    //it could be handled by a flag column or something like that. JPA even has annotation to help with that
    //and even if we used two databases, we could use outbox pattern or a transactional method to guarantee consistency
    //but for this example, this strategy should be enough
    private Boolean transferBetweenDatabases(UUID userId) {
        if (!db.containsKey(userId)) {
            HttpStatus notFound = HttpStatus.NOT_FOUND;
            throw HttpClientErrorException
                    .create("User doesn't exists", notFound, notFound.getReasonPhrase(), null, null, null);
        }
        disabledDb.put(userId, db.remove(userId));

        return disabledDb.containsKey(userId) && !db.containsKey(userId);
    }
}
