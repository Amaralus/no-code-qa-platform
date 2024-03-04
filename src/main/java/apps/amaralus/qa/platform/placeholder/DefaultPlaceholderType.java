package apps.amaralus.qa.platform.placeholder;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static apps.amaralus.qa.platform.placeholder.ValidationRules.FULL_PATH;
import static apps.amaralus.qa.platform.placeholder.ValidationRules.Rule.*;
import static java.util.stream.Collectors.toUnmodifiableMap;

@RequiredArgsConstructor
public enum DefaultPlaceholderType implements PlaceholderType {
    DATASET(FULL_PATH),
    PROJECT(new ValidationRules(EXCLUDE, INCLUDE)),
    FOLDER(FULL_PATH),
    TESTCASE(new ValidationRules(OPTIONAL, INCLUDE)),
    ENVIRONMENT(new ValidationRules(EXCLUDE, INCLUDE)),
    SERVICE(FULL_PATH),
    UNKNOWN(new ValidationRules(EXCLUDE, OPTIONAL));

    private static final Map<String, DefaultPlaceholderType> locations =
            Stream.of(values())
                    .filter(location -> location != UNKNOWN)
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    private final ValidationRules validationRules;

    public static DefaultPlaceholderType from(String name) {
        return name == null
                ? UNKNOWN
                : locations.getOrDefault(name.toUpperCase(), UNKNOWN);
    }

    @Override
    public ValidationRules getValidationRules() {
        return validationRules;
    }
}
