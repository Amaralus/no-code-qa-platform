package apps.amaralus.qa.platform.placeholder;

public interface PlaceholderLocation {

    static PlaceholderLocation from(String name) {
        PlaceholderLocation location = GeneratedLocation.from(name);
        if (location == null)
            location = DefaultLocation.from(name);
        return location;
    }
}
