package cloud.reivax.tiny_bank.services;

import cloud.reivax.tiny_bank.services.models.accounts.AccountModel;
import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;

import java.util.List;

public interface AccountService {

    List<AccountModel> getAccounts(String[] accountIds);

    void executeTransaction(TransactionModel transactionModel);


}
