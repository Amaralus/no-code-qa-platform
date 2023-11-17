package apps.amaralus.qa.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@Getter
@AllArgsConstructor
public enum Placeholder {

    RANDOM_UUID("randomUuid"),
    TIMESTAMP("timestamp"),
    RANDOM_WORD("randomWord"),
    RANDOM_NUMBER("randomNumber");

    private final String name;

    public static Placeholder getByName(String name) {
        return EnumSet.allOf(Placeholder.class)
                .stream()
                .filter(placeholder -> placeholder.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
