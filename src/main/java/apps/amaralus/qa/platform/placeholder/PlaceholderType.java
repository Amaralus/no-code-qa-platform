package apps.amaralus.qa.platform.placeholder;

import apps.amaralus.qa.platform.placeholder.generate.GeneratedPlaceholderType;

public interface PlaceholderType {

    static PlaceholderType from(String name) {
        PlaceholderType location = GeneratedPlaceholderType.from(name);
        if (location == null)
            location = DefaultPlaceholderType.from(name);
        return location;
    }

    ValidationRules getValidationRules();
}
