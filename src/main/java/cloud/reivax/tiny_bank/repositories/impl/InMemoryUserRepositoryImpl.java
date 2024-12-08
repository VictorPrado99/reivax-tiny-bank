package cloud.reivax.tiny_bank.repositories.impl;

import cloud.reivax.tiny_bank.repositories.UserRepository;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.InMemoryDbUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class InMemoryUserRepositoryImpl implements UserRepository {

    private final Map<UUID, UserEntity> db;

    @Override
    public UserEntity findById(UUID userId) {
        if (!db.containsKey(userId)) {
            //I choose to throw exception here, because I implemented the "storage", but usually that would probably be at Service Layer
            ExceptionThrower.throw404("User not found");
        }
        return db.get(userId);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        // I am not worrying about updating user, but theoretically this same method could be used for it,
        // but, we may want further logic to keep historical data or something like that.
        UUID userId = InMemoryDbUtility.getOrGenerateKey(userEntity.getUserId(), db.keySet());
        UserEntity savedEntity = userEntity.toBuilder()
                .userId(userId)
                .build();

        db.put(userId, savedEntity);

        return savedEntity;
    }

    @Override
    public void disableById(UUID userId) {
        if (!db.containsKey(userId)) {
            ExceptionThrower.throw404("User doesn't exists");
        }
        //Currently we just have way to disable, not re-enable, which means, disabled users
        //are locked from the system
        UserEntity user = db.get(userId);
        user.setUserEnabled(false);
    }
}
