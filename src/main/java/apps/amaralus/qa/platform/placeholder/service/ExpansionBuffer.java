package apps.amaralus.qa.platform.placeholder.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpansionBuffer {
    private boolean isFullyResolved;

    private final StringBuilder resolved = new StringBuilder();

    /**
     * RegExp поиск левого вложенного свойства
     */
    private static final Pattern NESTED_PROPERTY_PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    @NonNull
    private String unresolved;

    public ExpansionBuffer(String unresolved) {
        this.unresolved = StringUtils.defaultString(unresolved);
        this.isFullyResolved = !hasMoreLegalPlaceholders();
    }

    private boolean hasMoreLegalPlaceholders() {
        return NESTED_PROPERTY_PATTERN.matcher(unresolved).matches();
    }

    @Nullable
    public String extractNextPropertyKey() {
        String nextKeyToSearchFor = null;
        Matcher matcher = NESTED_PROPERTY_PATTERN.matcher(unresolved);
        if (matcher.find()) {
            nextKeyToSearchFor = matcher.group(1);
        } else {
            resolved.append(unresolved);
            isFullyResolved = true;
        }
        return nextKeyToSearchFor;
    }

    public void moveResolvedPartToNextProperty() {
        int start = unresolved.indexOf("{{");
        resolved.append(unresolved, 0, start);
        unresolved = unresolved.substring(unresolved.indexOf("}", start) + 2);
    }

    public String getFullyResolved() {
        if (!isFullyResolved) {
            throw new IllegalStateException("Property value is not fully resolved yet");
        }
        return resolved.toString();
    }

    @Override
    public String toString() {
        return "ExpansionBuffer{" + "isFullyResolved=" + isFullyResolved + ", resolved=" + resolved + ", unresolved='" + unresolved + '\'' + '}';
    }

    public void add(String resolvedProperty) {
        resolved.append(resolvedProperty);
    }
}
