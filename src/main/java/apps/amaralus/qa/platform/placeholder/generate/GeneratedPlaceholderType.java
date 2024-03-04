package apps.amaralus.qa.platform.placeholder.generate;


import apps.amaralus.qa.platform.placeholder.PlaceholderType;
import apps.amaralus.qa.platform.placeholder.ValidationRules;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static apps.amaralus.qa.platform.placeholder.ValidationRules.LOCATION_ONLY;
import static java.util.stream.Collectors.toUnmodifiableMap;

@RequiredArgsConstructor
public enum GeneratedPlaceholderType implements PlaceholderType {

    UUID(LOCATION_ONLY),
    TIMESTAMP(LOCATION_ONLY),
    NUMBER(LOCATION_ONLY),
    STRING(LOCATION_ONLY);

    private static final Map<String, GeneratedPlaceholderType> locations =
            Stream.of(values())
                    .collect(toUnmodifiableMap(
                            Enum::name,
                            Function.identity()
                    ));

    private final ValidationRules validationRules;

    public static GeneratedPlaceholderType from(String name) {
        return name == null ? null : locations.get(name.toUpperCase());
    }

    @Override
    public ValidationRules getValidationRules() {
        return validationRules;
    }
}
