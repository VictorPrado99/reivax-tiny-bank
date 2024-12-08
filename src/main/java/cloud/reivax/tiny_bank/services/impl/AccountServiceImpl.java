package cloud.reivax.tiny_bank.services.impl;

import cloud.reivax.tiny_bank.repositories.AccountRepository;
import cloud.reivax.tiny_bank.repositories.entities.AccountEntity;
import cloud.reivax.tiny_bank.services.AccountService;
import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.processors.TransactionProcessor;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import cloud.reivax.tiny_bank.utils.mappers.AccountMapper;
import cloud.reivax.tiny_bank.utils.mappers.TransactionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    @Override
    public List<AccountModel> getAccounts(String[] accountIdsArray) {
        if (accountIdsArray == null) {
            return accountRepository.findAllAccounts().stream()
                    .map(AccountMapper.INSTANCE::entityToModel)
                    .toList();
        }

        return Arrays.stream(accountIdsArray)
                .filter(Objects::nonNull)
                .filter(String::isBlank)
                .map(this::getUuidOrNull)
                .filter(Objects::nonNull)
                .map(accountRepository::findAccount)
                .filter(Objects::nonNull)
                .map(AccountMapper.INSTANCE::entityToModel)
                .toList();
    }

    private UUID getUuidOrNull(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            log.info("String {} is not an UUID", id);
            return null;
        }
    }

    @Override
    public void executeTransaction(TransactionModel transactionModel) {
        TransactionProcessor transactionProcessor = transactionModel.transactionType().getTransactionProcessor();

        transactionProcessor.process(transactionModel);
    }

    @Override
    public AccountModel createAccount(UUID userId) {
        AccountEntity accountEntity = AccountEntity.builder()
                .userId(userId)
                .transactionHistory(new LinkedList<>())
                .build();

        AccountEntity newAccount = accountRepository.save(accountEntity);

        return AccountMapper.INSTANCE.entityToModel(newAccount);
    }

    @Override
    public AccountModel retrieveAccount(UUID accountId) {
        AccountEntity accountEntity = accountRepository.findAccount(accountId);
        if (accountEntity == null) {
            ExceptionThrower.throw404("Account not found");
        }

        return AccountMapper.INSTANCE.entityToModel(accountEntity);
    }

    @Override
    public List<TransactionModel> retrieveTransactionHistory(UUID accountId) {
        AccountEntity accountEntity = accountRepository.findAccount(accountId);

        return accountEntity.transactionHistory().stream()
                .map(TransactionMapper.INSTANCE::entityToModel)
                .toList();
    }
}
