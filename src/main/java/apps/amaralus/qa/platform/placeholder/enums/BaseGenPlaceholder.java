package apps.amaralus.qa.platform.placeholder.enums;

import apps.amaralus.qa.platform.placeholder.enums.impl.NumberGenerator;
import apps.amaralus.qa.platform.placeholder.enums.impl.StringGenerator;
import apps.amaralus.qa.platform.placeholder.enums.impl.TimestampGenerator;
import apps.amaralus.qa.platform.placeholder.enums.impl.UUIDGenerator;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum BaseGenPlaceholder implements PlaceholderGenerator {

    UUID(new UUIDGenerator()),
    TIMESTAMP(new TimestampGenerator()),
    STRING(new StringGenerator()),
    NUMBER(new NumberGenerator());


    private static final Map<String, Object> genValueMap;

    static {
        genValueMap = EnumSet.allOf(BaseGenPlaceholder.class).stream()
                .collect(Collectors.toMap(
                        placeholder -> placeholder.name().toLowerCase(),
                        BaseGenPlaceholder::generateValue)
                );
    }

    private final PlaceholderGenerator placeholderGenerator;

    @Override
    public Object generateValue() {
        return placeholderGenerator.generateValue();
    }

    public static Optional<Object> getOptionalByPlaceholder(String name) {
        return Optional.ofNullable(genValueMap.get(name.toLowerCase()));
    }

    public static Object getValueByPlaceholder(String name) {
        return getOptionalByPlaceholder(name)
                .orElse(null);
    }
}
