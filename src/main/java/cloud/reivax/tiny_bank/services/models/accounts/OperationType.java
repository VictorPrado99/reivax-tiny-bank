package cloud.reivax.tiny_bank.services.models.accounts;

import lombok.AllArgsConstructor;

import java.util.function.BiFunction;

@AllArgsConstructor
public enum OperationType {
    ADD(Double::sum),
    SUBTRACT((currentBalance, changingAmount) -> currentBalance - changingAmount);

    final BiFunction<Double, Double, Double> operationFunction;
}
