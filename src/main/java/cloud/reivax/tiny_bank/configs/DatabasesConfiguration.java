package cloud.reivax.tiny_bank.configs;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DatabasesConfiguration {

    @Bean
    public Map<UUID, UserEntity> db() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<UUID, UserEntity> disabledDb() {
        return new ConcurrentHashMap<>();
    }

}
