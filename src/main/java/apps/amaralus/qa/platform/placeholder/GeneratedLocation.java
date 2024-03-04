package apps.amaralus.qa.platform.placeholder;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public enum GeneratedLocation implements PlaceholderLocation {

    UUID,
    TIMESTAMP,
    NUMBER,
    STRING;

    private static final Map<String, GeneratedLocation> locations =
            Stream.of(values())
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    public static GeneratedLocation from(String name) {
        return name == null ? null : locations.get(name.toUpperCase());
    }
}
