package cloud.reivax.tiny_bank.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
@Component
public class TinyBankExceptionHandler {

    @ExceptionHandler(exception = {HttpStatusCodeException.class})
    public ErrorResponse handleExceptions(HttpStatusCodeException exception) {
        return ErrorResponse.create(exception, exception.getStatusCode(), exception.getMessage());
    }
}
