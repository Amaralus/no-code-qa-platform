package apps.amaralus.qa.platform.placeholder.generate;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return UUID.randomUUID();
    }

    @Override
    public GeneratedLocationType getPlaceholderLocation() {
        return GeneratedLocationType.UUID;
    }
}
