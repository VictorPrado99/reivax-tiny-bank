package cloud.reivax.tiny_bank.services.models;

import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity userModelToEntity(UserModel userModel);

    UserModel userEntityToModel(UserEntity userEntity);
}
