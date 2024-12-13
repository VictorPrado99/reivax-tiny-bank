package cloud.reivax.tiny_bank.api.controllers.impl;

import cloud.reivax.tiny_bank.api.dtos.users.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.utils.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static cloud.reivax.tiny_bank.utility.DbHelperTester.createUserInDb;
import static cloud.reivax.tiny_bank.utility.ExceptionHelperTester.validateExceptionThrown;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UsersControllerImplTest {

    @Autowired
    Map<UUID, UserEntity> db;

    @Autowired
    UsersControllerImpl usersController;

    @Test
    void testCreateUserWithSuccess() {
        //Given
        String userName = "dummy_user";
        CreateUserDto createUserDto = new CreateUserDto(userName);

        //When
        ResponseEntity<UserDto> responseEntity = usersController.createUser(createUserDto);

        UserDto body = responseEntity.getBody();
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        //Then
        assertEquals(HttpStatus.CREATED, statusCode);
        assertNotNull(body.userId());
        assertNotNull(body.accounts());
        assertEquals(userName, body.userName());
    }

    @Test
    void testDisableUserWithSuccess() {
        //Given

        //We have a user in the database
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "dummy_user";

        createUserInDb(userId, accountId, userName, db);

        //When
        ResponseEntity<Void> responseEntity = usersController.deactivateUser(userId.toString());
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        //Then
        assertEquals(HttpStatus.OK, statusCode);
        assertFalse(db.get(userId).isUserEnabled());

    }

    //Here is a perfect example which would be nice to have a ParametrizedTests
    @Test
    void testDisableMissingUser() {
        validateExceptionThrown(usersController::deactivateUser, HttpStatus.NOT_FOUND);
    }

    @Test
    void testTryDisableNotValidUser() {
        validateExceptionThrown(usersController::deactivateUser, HttpStatus.NOT_ACCEPTABLE, "XPTO");
    }

    @Test
    void testTryToGetNonExistentUser() {
        validateExceptionThrown(usersController::getUser, HttpStatus.NOT_FOUND);
    }

    @Test
    void testTryGetNonValidUser() {
        validateExceptionThrown(usersController::getUser, HttpStatus.NOT_ACCEPTABLE, "xpto");
    }

    @Test
    void testGetExistentUser() {
        //Given
        //We have a user in the database
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        String userName = "dummy_user";

        UserEntity userInDbEntity = createUserInDb(userId, accountId, userName, db);
        UserMapper mapper = UserMapper.INSTANCE;
        UserDto userInDbDto = mapper.userModelToUserDto(mapper.userEntityToModel(userInDbEntity));

        //When
        ResponseEntity<UserDto> responseEntity = usersController.getUser(userId.toString());
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        UserDto userDto = responseEntity.getBody();

        //Then
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(userInDbDto, userDto);

    }

}
