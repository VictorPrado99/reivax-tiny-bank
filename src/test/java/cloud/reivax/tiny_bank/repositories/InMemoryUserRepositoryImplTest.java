package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InMemoryUserRepositoryImplTest {

    private InMemoryUserRepositoryImpl inMemoryUserRepository;

    private final Map<UUID, UserEntity> db = new ConcurrentHashMap<>();

    private final Map<UUID, UserEntity> disabledDb = new ConcurrentHashMap<>();


    @BeforeEach
    void setUp() {
        inMemoryUserRepository = new InMemoryUserRepositoryImpl(db, disabledDb);
        db.clear();
        disabledDb.clear();
    }

    @Test
    void checkCorrectlySavedEntity() {
        //Given
        String userName = "xpto";
        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .build();

        //When
        UUID userId = inMemoryUserRepository.save(userEntity);
        userEntity = userEntity.toBuilder()
                .userId(userId)
                .build();

        //Then
        assertTrue(db.containsKey(userId));
        assertEquals(userEntity, db.get(userId));
    }

    @Test
    void validateDisabledSuccessful() {
        //Given
        UUID userId = UUID.randomUUID();
        String userName = "xpto";
        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .userName(userName)
                .build();

        //DB Previously has the user
        db.put(userId, userEntity);

        //When
        inMemoryUserRepository.disableById(userId);

        //Then
        assertFalse(db.containsKey(userId));
        assertTrue(disabledDb.containsKey(userId));
        assertEquals(userEntity, disabledDb.get(userId));
    }
}