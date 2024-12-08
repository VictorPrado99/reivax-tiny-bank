package cloud.reivax.tiny_bank.utils.mappers;

import cloud.reivax.tiny_bank.api.dtos.accounts.AccountDto;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto modelToDto(AccountModel accountModel);

    AccountModel entityToModel(AccountEntity entity);

    AccountEntity modelToEntity(AccountModel accountModel);

}
