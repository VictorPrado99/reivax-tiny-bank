package cloud.reivax.tiny_bank.services.impl;

import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.repositories.UserRepository;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.services.AccountService;
import cloud.reivax.tiny_bank.services.UserManagementService;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.users.UserModel;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import cloud.reivax.tiny_bank.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UserDto createUser(UserModel user) {
        UserMapper userMapper = UserMapper.INSTANCE;
        UserEntity userEntity = userRepository.save(userMapper.userModelToEntity(user));
        AccountModel accountModel = accountService.createAccount(userEntity.getUserId());
        userEntity.addAccount(AccountMapper.INSTANCE.modelToEntity(accountModel));

        return userMapper.userModelToUserDto(userMapper.userEntityToModel(userEntity));
    }

    @Override
    public void disableUser(UUID userId) {
        userRepository.disableById(userId);
    }
}
