package apps.amaralus.qa.platform.placeholder;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public enum DefaultLocationType implements PlaceholderLocationType {
    DATASET,
    PROJECT,
    FOLDER,
    TESTCASE,
    ENVIRONMENT,
    SERVICE,
    UNKNOWN;

    private static final Map<String, DefaultLocationType> locations =
            Stream.of(values())
                    .filter(location -> location != UNKNOWN)
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    public static DefaultLocationType from(String name) {
        return name == null
                ? UNKNOWN
                : locations.getOrDefault(name.toUpperCase(), UNKNOWN);
    }
}
