package cloud.reivax.tiny_bank.api.controllers.users;

import cloud.reivax.tiny_bank.api.controllers.UsersController;
import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.api.dtos.users.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.services.UserManagementService;
import cloud.reivax.tiny_bank.services.models.users.UserModel;
import cloud.reivax.tiny_bank.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {

    private final UserManagementService userManagementService;
    private static final String BASE_RESOURCE_PATH = "/users/";


    @Override
    public ResponseEntity<UserDto> getUser(String userIdParam) {
        UserModel user = userManagementService.getUser(parseUUIDString(userIdParam));
        UserDto userDto = UserMapper.INSTANCE.userModelToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<AccountDto> createUser(CreateUserDto createUserDto) {
        UserModel userModel = UserMapper.INSTANCE.createUserDtoToModel(createUserDto);
        AccountDto accountDto = userManagementService.createUser(userModel);

        return ResponseEntity.created(URI.create(BASE_RESOURCE_PATH + accountDto.user().userId()))
                .body(accountDto);

    }

    @Override
    public ResponseEntity<Void> deactivateUser(String userIdParam) {
        UUID userId = parseUUIDString(userIdParam);
        userManagementService.disableUser(userId);

        return ResponseEntity.ok().build();
    }

    private UUID parseUUIDString(String userIdString) {
        try {
            return UUID.fromString(userIdString);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw HttpClientErrorException.NotAcceptable.create(
                    HttpStatus.NOT_ACCEPTABLE,
                    HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
    }
}
