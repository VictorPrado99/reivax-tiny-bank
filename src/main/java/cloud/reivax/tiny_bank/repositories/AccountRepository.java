package cloud.reivax.tiny_bank.repositories;

import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.repositories.entities.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface AccountRepository {

    AccountEntity save(AccountEntity accountEntity);

    AccountEntity findAccount(UUID accountId);

    TransactionEntity findTransaction(UUID transactionId);

    List<AccountEntity> findAllAccounts();
}
