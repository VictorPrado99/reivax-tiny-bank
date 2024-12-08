package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.services.models.UserModel;

import java.net.URI;
import java.util.UUID;

public interface UserManagementService {

    UserModel getUser(UUID userId);

    URI createUser(UserModel user);

    void disableUser(UUID userId);
}
