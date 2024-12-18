package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountModel> getAccounts(String[] accountIds);
    void executeTransaction(TransactionModel transactionModel);
    AccountModel createAccount(UUID userId);
    AccountModel retrieveAccount(UUID accountId);

    List<TransactionModel> retrieveTransactionHistory(UUID accountId);

}
