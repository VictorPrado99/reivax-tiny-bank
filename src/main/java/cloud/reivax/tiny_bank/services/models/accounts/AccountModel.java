package cloud.reivax.tiny_bank.services.models.accounts;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class AccountModel {
    private final UUID accountId;
    private final UUID userId;
    private double balance;

    @Setter(AccessLevel.NONE)
    private final List<TransactionModel> transactionHistory = new LinkedList<>();

    public void processTransaction(TransactionModel transaction, OperationType operationType) {
        balance = operationType.operationFunction.apply(balance, transaction.amount());
        addTransactionToHistory(transaction);
    }

    private void addTransactionToHistory(TransactionModel transaction) {
        transactionHistory.add(transaction);
    }
}
