package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;

import java.util.UUID;

public interface UserRepository {

    UserEntity findById(UUID userId);

    UUID save(UserEntity userEntity);

    Boolean disableById(UUID userId);
}
