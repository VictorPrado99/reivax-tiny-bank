package cloud.reivax.tiny_bank.services.processors;

import cloud.reivax.tiny_bank.services.processors.impl.DepositTransactionProcessor;
import cloud.reivax.tiny_bank.services.processors.impl.TransferTransactionProcessor;
import cloud.reivax.tiny_bank.services.processors.impl.UnkownTransactionProcessor;
import cloud.reivax.tiny_bank.services.processors.impl.WithdrawTransactionProcessor;
import cloud.reivax.tiny_bank.utils.ApplicationContextProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Slf4j
public enum TransactionType {
    UNKNOWN(UnkownTransactionProcessor.class),
    DEPOSIT(DepositTransactionProcessor.class),
    WITHDRAW(WithdrawTransactionProcessor.class),
    TRANSFER(TransferTransactionProcessor.class)
    ;

    private final Class<? extends TransactionProcessor> clazz;

    public static TransactionType getByName(String typeName) {
        return Arrays.stream(values())
                .filter(transactionType -> transactionType.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public TransactionProcessor getTransactionProcessor() {
        //This can break, but if it does, it means that we didn't set the bean, and we should fix it.
        return ApplicationContextProvider.INSTANCE.getApplicationContext().getBean(clazz);
    }

}
