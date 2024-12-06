package cloud.reivax.tiny_bank.api.dtos;

import cloud.reivax.tiny_bank.services.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    UserModel createUserDtoToModel(CreateUserDto createUserDto);

    UserDto userModelToUserDto(UserModel userModel);
}
