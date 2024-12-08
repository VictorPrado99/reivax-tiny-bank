package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.services.models.users.UserModel;

import java.util.UUID;

public interface UserManagementService {

    UserModel getUser(UUID userId);

    UserDto createUser(UserModel user);

    void disableUser(UUID userId);

    void enableUser(UUID userId);
}
