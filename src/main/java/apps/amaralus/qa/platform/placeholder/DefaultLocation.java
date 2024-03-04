package apps.amaralus.qa.platform.placeholder;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public enum DefaultLocation implements PlaceholderLocation {
    DATASET,
    PROJECT,
    FOLDER,
    TESTCASE,
    ENVIRONMENT,
    SERVICE,
    ALIAS;

    private static final Map<String, DefaultLocation> locations =
            Stream.of(values())
                    .filter(location -> location != ALIAS)
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    public static DefaultLocation from(String name) {
        return name == null
                ? ALIAS
                : locations.getOrDefault(name.toUpperCase(), ALIAS);
    }
}
