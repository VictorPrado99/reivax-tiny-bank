package cloud.reivax.tiny_bank.services.impl;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.repositories.UserRepository;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.services.AccountService;
import cloud.reivax.tiny_bank.services.UserManagementService;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.users.UserModel;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import cloud.reivax.tiny_bank.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    @Override
    public UserModel getUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId);
        return UserMapper.INSTANCE.userEntityToModel(userEntity);
    }

    @Override
    public AccountDto createUser(UserModel user) {
        UserMapper userMapper = UserMapper.INSTANCE;
        UserEntity userEntity = userRepository.save(userMapper.userModelToEntity(user));

        AccountModel accountModel = accountService.createAccount(userMapper.userEntityToModel(userEntity));

        return AccountMapper.INSTANCE.modelToDto(accountModel);
    }

    @Override
    public void disableUser(UUID userId) {
        if (!userRepository.disableById(userId)) {
            HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
            throw HttpServerErrorException.ServiceUnavailable
                    .create("Error on disabling user",
                            internalServerError,
                            internalServerError.getReasonPhrase(),
                            null,
                            null,
                            StandardCharsets.UTF_8);
        }
    }
}
