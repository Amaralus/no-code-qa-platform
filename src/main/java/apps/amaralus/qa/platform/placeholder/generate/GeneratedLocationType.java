package apps.amaralus.qa.platform.placeholder.generate;


import apps.amaralus.qa.platform.placeholder.PlaceholderLocationType;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public enum GeneratedLocationType implements PlaceholderLocationType {

    UUID,
    TIMESTAMP,
    NUMBER,
    STRING;

    private static final Map<String, GeneratedLocationType> locations =
            Stream.of(values())
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    public static GeneratedLocationType from(String name) {
        return name == null ? null : locations.get(name.toUpperCase());
    }
}
