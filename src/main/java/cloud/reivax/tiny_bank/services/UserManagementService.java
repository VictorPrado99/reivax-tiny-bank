package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.services.models.users.UserModel;

import java.util.UUID;

public interface UserManagementService {

    UserModel getUser(UUID userId);

    AccountDto createUser(UserModel user);

    void disableUser(UUID userId);
}
