package apps.amaralus.qa.platform.placeholder;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Placeholder {

    public static final Pattern PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    private final String entity;
    private final Long id;
    private final String variable;

    public static Placeholder parse(String placeholderString) {
        return new Placeholder(placeholderString);
    }

    private Placeholder(String placeholderString) {
        var matcher = PATTERN.matcher(placeholderString);
        if (!matcher.matches())
            throwIllegalArgumentException(placeholderString);

        var variableSplit = matcher.group(1).split(":");
        if (variableSplit.length == 1) {
            variable = variableSplit[0];
            entity = null;
            id = null;
        } else {
            variable = variableSplit[1];
            var entitySplit = variableSplit[0].split("#");
            if (entitySplit.length == 1) {
                entity = entitySplit[0];
                id = null;
            } else {
                entity = entitySplit[0];
                try {
                    id = Long.parseLong(entitySplit[1]);
                    if (id <= 0)
                        throwIllegalArgumentException(placeholderString, " id value <= 0");
                } catch (NumberFormatException e) {
                    // нельзя заменить на метод и сохранить final id из-за особенности компиляции
                    throw new IllegalArgumentException(
                            "Invalid placeholder \"" + placeholderString + "\" Invalid id: " + e.getMessage());
                }
            }
        }
    }

    private void throwIllegalArgumentException(String placeholderString) {
        throwIllegalArgumentException(placeholderString, "");
    }

    private void throwIllegalArgumentException(String placeholderString, String extraMessage) {
        throw new IllegalArgumentException("Invalid placeholder \"" + placeholderString + "\"." + extraMessage);
    }
}
