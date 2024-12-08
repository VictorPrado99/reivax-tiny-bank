package cloud.reivax.tiny_bank.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class UuidUtility {
    public UUID parseUUIDString(String userIdString) {
        try {
            return UUID.fromString(userIdString);
        } catch (IllegalArgumentException illegalArgumentException) {
            ExceptionThrower.throw406("Not acceptable UUID");
        }
        return null;
    }
}
