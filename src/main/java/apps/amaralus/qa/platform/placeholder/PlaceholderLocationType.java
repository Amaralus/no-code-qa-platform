package apps.amaralus.qa.platform.placeholder;

import apps.amaralus.qa.platform.placeholder.generate.GeneratedLocationType;

public interface PlaceholderLocationType {

    static PlaceholderLocationType from(String name) {
        PlaceholderLocationType location = GeneratedLocationType.from(name);
        if (location == null)
            location = DefaultLocationType.from(name);
        return location;
    }
}
