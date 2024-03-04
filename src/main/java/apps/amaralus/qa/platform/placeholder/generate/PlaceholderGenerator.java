package apps.amaralus.qa.platform.placeholder.generate;

public interface PlaceholderGenerator {
    Object generateValue();

    GeneratedLocationType getPlaceholderLocation();
}
