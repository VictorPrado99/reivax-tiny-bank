package cloud.reivax.tiny_bank.services.processors;

import cloud.reivax.tiny_bank.services.models.accounts.TransactionModel;

public interface TransactionProcessor {
    // We are using beans here, but in a real application, the architecture would look a lot different,
    // we would probably need to do threads, and possibly outbox pattern for speed and consistency
    void process(TransactionModel transaction);

}
