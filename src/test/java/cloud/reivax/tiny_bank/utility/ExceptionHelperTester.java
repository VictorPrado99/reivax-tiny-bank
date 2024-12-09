package cloud.reivax.tiny_bank.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.fail;

public final class ExceptionHelperTester {

    public static void validateExceptionThrown(Consumer<String> methodToCall, HttpStatus expectedStatus) {
        //Given
        //We have a random userId for a missing user
        UUID userId = UUID.randomUUID();
        validateExceptionThrown(methodToCall, expectedStatus, userId.toString());
    }

    public static void validateExceptionThrown(Consumer<String> methodToCall, HttpStatus expectedStatus, String userId) {
        //When Then
        boolean success = false;
        try {
            methodToCall.accept(userId);
        } catch (HttpStatusCodeException statusCodeException) {
            //I could use assertThrows, but chose to do this way, just for preference
            if (statusCodeException.getStatusCode().equals(expectedStatus)) {
                success = true;
            }
        } catch (Exception e) {
            fail();
        }

        if (!success) fail();
    }

}
