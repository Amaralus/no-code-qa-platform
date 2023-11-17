package apps.amaralus.qa.platform.util;

import apps.amaralus.qa.platform.enums.Placeholder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

import static apps.amaralus.qa.platform.enums.Placeholder.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceholderUtils {

    private static final Map<Placeholder, Supplier<Object>> placeholdersMap;

    static {
        placeholdersMap = Map.of(
                RANDOM_UUID, GeneratorUtils::randomUuid,
                TIMESTAMP, GeneratorUtils::timestamp,
                RANDOM_WORD, GeneratorUtils::randomWord,
                RANDOM_NUMBER, GeneratorUtils::randomNumber
        );
    }

    public static Object resolvePlaceholder(String name) {
        Placeholder placeholder = getByName(name);
        return placeholdersMap.getOrDefault(placeholder, () -> name).get();
    }
}
