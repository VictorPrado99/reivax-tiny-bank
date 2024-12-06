package cloud.reivax.tiny_bank.exceptions;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;


@RestControllerAdvice
public class TinyBankExceptionHandler {

    @ExceptionHandler(exception = {HttpStatusCodeException.class})
    public ErrorResponse handleExceptions(HttpStatusCodeException exception) {
        return ErrorResponse.create(exception, exception.getStatusCode(), exception.getMessage());
    }
}
