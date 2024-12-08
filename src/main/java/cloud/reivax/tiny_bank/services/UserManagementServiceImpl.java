package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.repositories.UserRepository;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.services.models.UserEntityMapper;
import cloud.reivax.tiny_bank.services.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private static final String BASE_RESOURCE_PATH = "/users/";

    private final UserRepository userRepository;

    @Override
    public UserModel getUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId);
        return UserEntityMapper.INSTANCE.userEntityToModel(userEntity);
    }

    @Override
    public URI createUser(UserModel user) {
        UserEntity userEntity = UserEntityMapper.INSTANCE.userModelToEntity(user);
        UUID userId = userRepository.save(userEntity);

        return URI.create(BASE_RESOURCE_PATH + userId);
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
