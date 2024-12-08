package cloud.reivax.tiny_bank.utils.mappers;

import cloud.reivax.tiny_bank.api.dtos.accounts.CreateTransactionDto;
import cloud.reivax.tiny_bank.api.dtos.accounts.TransactionDto;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "transactionId", ignore = true)
    TransactionModel dtoToTransactionModel(CreateTransactionDto createTransactionDto);

    TransactionModel entityToModel(TransactionEntity transactionEntity);

    TransactionEntity modelToEntity(TransactionModel transactionModel);

    TransactionDto modelToDto(TransactionModel transactionModel);


}
