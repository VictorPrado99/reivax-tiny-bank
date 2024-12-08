package cloud.reivax.tiny_bank.services.models.accounts;

import cloud.reivax.tiny_bank.services.models.users.UserModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class AccountModel {
    private final UUID accountId;
    private final UserModel user;
    @Setter(AccessLevel.NONE)
    private Double balance;

    @Setter(AccessLevel.NONE)
    private final List<TransactionModel> transactionHistory = new LinkedList<>();

    public void processTransaction(TransactionModel transaction, OperationType operationType) {
        if (balance == null) balance = 0D;
        operationType.operationFunction.apply(balance, transaction.amount());
        addTransactionToHistory(transaction);
    }

    private void addTransactionToHistory(TransactionModel transaction) {
        transactionHistory.add(transaction);
    }
}
