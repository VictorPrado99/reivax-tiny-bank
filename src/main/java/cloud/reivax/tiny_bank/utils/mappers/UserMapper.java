package cloud.reivax.tiny_bank.utils.mappers;

import cloud.reivax.tiny_bank.api.dtos.users.CreateUserDto;
import cloud.reivax.tiny_bank.api.dtos.users.UserDto;
import cloud.reivax.tiny_bank.repositories.entities.UserEntity;
import cloud.reivax.tiny_bank.services.models.users.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "isUserEnabled", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    UserModel createUserDtoToModel(CreateUserDto createUserDto);

    UserDto userModelToUserDto(UserModel userModel);

    UserEntity userModelToEntity(UserModel userModel);

    @Mapping(target = "isUserEnabled", ignore = true)
    UserModel userEntityToModel(UserEntity userEntity);
}
