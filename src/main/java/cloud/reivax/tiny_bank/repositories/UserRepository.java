package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;

import java.util.UUID;

public interface UserRepository {

    //This interface, is cool mainly for two things, first of all is easy to adapt to use a real database later on,
    // second is good practice depends on abstraction, an example why that would be good practice, is the example above,
    // we can easily change the implementation without needing to change the service layer
    UserEntity findById(UUID userId);

    UserEntity save(UserEntity userEntity);

    void disableById(UUID userId);
}
