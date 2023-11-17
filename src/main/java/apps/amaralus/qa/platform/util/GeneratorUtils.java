package apps.amaralus.qa.platform.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratorUtils {

    public static Supplier<Object> randomUuid() {
        return UUID::randomUUID;
    }

    public static Supplier<Object> timestamp() {
        return Instant::now;
    }

    public static Supplier<Object> randomWord() {
        return () -> RandomStringUtils.randomAlphabetic(5);
    }

    public static Supplier<Object> randomNumber() {
        return () -> RandomStringUtils.randomNumeric(5);
    }
}
