package cloud.reivax.tiny_bank.exceptions;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;


@RestControllerAdvice
public class TinyBankExceptionHandler {

    @ExceptionHandler(exception = {HttpStatusCodeException.class})
    public ErrorResponse handleExceptions(HttpStatusCodeException exception) {
        //A simple exception handler, so I can throw exceptions in the application, and it will generate the ErrorResponse here,
        // but it is very simple, and not very robust
        return ErrorResponse.create(exception, exception.getStatusCode(), exception.getMessage());
    }
}
