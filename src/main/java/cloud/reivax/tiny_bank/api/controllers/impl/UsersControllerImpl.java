package cloud.reivax.tiny_bank.api.controllers.impl;

import cloud.reivax.tiny_bank.api.controllers.UsersController;
import cloud.reivax.tiny_bank.api.dtos.users.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.services.UserManagementService;
import cloud.reivax.tiny_bank.services.models.users.UserModel;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.UuidUtility;
import cloud.reivax.tiny_bank.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {

    private final UserManagementService userManagementService;
    private static final String BASE_RESOURCE_PATH = "/users/";


    @Override
    public ResponseEntity<UserDto> getUser(String userIdParam) {
        UUID userId = UuidUtility.parseUUIDString(userIdParam);
        if (userId == null) {
            ExceptionThrower.throwUuidNotComplain();
        }

        UserModel user = userManagementService.getUser(userId);
        UserDto userDto = UserMapper.INSTANCE.userModelToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> createUser(CreateUserDto createUserDto) {
        UserModel userModel = UserMapper.INSTANCE.createUserDtoToModel(createUserDto);
        UserDto userDto = userManagementService.createUser(userModel);

        return ResponseEntity.created(URI.create(BASE_RESOURCE_PATH + userDto.userId()))
                .body(userDto);

    }

    @Override
    public ResponseEntity<Void> deactivateUser(String userIdParam) {
        UUID userId = UuidUtility.parseUUIDString(userIdParam);
        if (userId == null) {
            ExceptionThrower.throwUuidNotComplain();
        }

        userManagementService.disableUser(userId);
        return ResponseEntity.ok().build();
    }


}
