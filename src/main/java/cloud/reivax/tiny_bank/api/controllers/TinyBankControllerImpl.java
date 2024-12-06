package cloud.reivax.tiny_bank.api.controllers;

import cloud.reivax.tiny_bank.api.dtos.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.UserDto;
import cloud.reivax.tiny_bank.api.dtos.UserMapper;
import cloud.reivax.tiny_bank.services.UserManagementService;
import cloud.reivax.tiny_bank.services.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class TinyBankControllerImpl implements TinyBankController {

    private final UserManagementService userManagementService;

    @Override
    public ResponseEntity<UserDto> getUser(String userIdParam) {
        UserModel user = userManagementService.getUser(parseUUIDString(userIdParam));
        UserDto userDto = UserMapper.INSTANCE.userModelToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<Void> createUser(CreateUserDto createUserDto) {
        UserModel userModel = UserMapper.INSTANCE.createUserDtoToModel(createUserDto);
        URI resourcePath = userManagementService.createUser(userModel);

        return ResponseEntity.created(resourcePath).build();

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
