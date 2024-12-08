package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.models.users.UserModel;

import java.util.List;

public interface AccountService {

    List<AccountModel> getAccounts(String[] accountIds);

    void executeTransaction(TransactionModel transactionModel);

    AccountModel createAccount(UserModel userId);

}
