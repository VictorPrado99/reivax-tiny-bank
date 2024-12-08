package cloud.reivax.tiny_bank.services.processors.impl;

import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.processors.TransactionProcessor;
import org.springframework.stereotype.Component;

@Component
public class WithdrawTransactionProcessor implements TransactionProcessor {
    @Override
    public void process(TransactionModel transaction) {

    }
}
