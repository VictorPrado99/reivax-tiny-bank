package cloud.reivax.tiny_bank.services.processors.impl;

import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;
import cloud.reivax.tiny_bank.services.processors.TransactionProcessor;
import cloud.reivax.tiny_bank.utils.ExceptionThrower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UnkownTransactionProcessor implements TransactionProcessor {
    @Override
    public void process(TransactionModel transaction) {
        log.warn("Unknown processor used");
        ExceptionThrower.throw406("Not recognized TransactionType");
    }
}
