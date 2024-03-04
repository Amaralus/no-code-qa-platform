package apps.amaralus.qa.platform.placeholder;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Placeholder {

    public static final Pattern BRACES_PATTERN = Pattern.compile("\\{\\{(.*?)}}");
    public static final Pattern LOCATION_PATTERN = Pattern.compile("([a-zA-Z0-9-_]+)");
    public static final Pattern LOCATION_VARIABLE_PATTERN =
            Pattern.compile(LOCATION_PATTERN + ":" + LOCATION_PATTERN);
    public static final Pattern FULL_PATH_PATTERN =
            Pattern.compile(LOCATION_PATTERN + "#(\\d+):" + LOCATION_PATTERN);

    private final String path;
    private final String location;
    private final PlaceholderLocationType locationType;
    private final Long id;
    private final String variable;

    public static Placeholder parse(String placeholderString) {
        return new Placeholder(placeholderString);
    }

    private Placeholder(String placeholderString) {
        var matcher = BRACES_PATTERN.matcher(placeholderString);
        if (!matcher.matches() || matcher.group(1).isBlank())
            throw new InvalidPlaceholderException(placeholderString);

        path = matcher.group(1);

        var locationMatcher = LOCATION_PATTERN.matcher(path);
        var locationVariableMatcher = LOCATION_VARIABLE_PATTERN.matcher(path);
        var fullPathMatcher = FULL_PATH_PATTERN.matcher(path);

        if (locationMatcher.matches()) {
            location = path;
            id = null;
            variable = null;
        } else if (locationVariableMatcher.matches()) {
            location = locationVariableMatcher.group(1);
            id = null;
            variable = locationVariableMatcher.group(2);
        } else if (fullPathMatcher.matches()) {
            location = fullPathMatcher.group(1);
            id = Long.valueOf(fullPathMatcher.group(2));
            variable = fullPathMatcher.group(3);
        } else
            throw new InvalidPlaceholderException(placeholderString);

        locationType = PlaceholderLocationType.from(location);
    }

    @Override
    public String toString() {
        return "{{" + path + "}}";
    }
}
