package cloud.reivax.tiny_bank.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class ExceptionThrower {

    public void throw404(String msg) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        throw HttpClientErrorException.create(msg,
                notFound,
                notFound.getReasonPhrase(),
                null,
                null,
                StandardCharsets.UTF_8);
    }

    public void throwUuidNotComplain() {
        throw406("Incorrect UUID");
    }

    public void throw406(String msg) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        throw HttpClientErrorException.create(msg,
                notAcceptable,
                notAcceptable.getReasonPhrase(),
                null,
                null,
                StandardCharsets.UTF_8);
    }

    public void throw422(String msg) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        throw HttpClientErrorException.create(msg,
                unprocessableEntity,
                unprocessableEntity.getReasonPhrase(),
                null,
                null,
                StandardCharsets.UTF_8);
    }

}
