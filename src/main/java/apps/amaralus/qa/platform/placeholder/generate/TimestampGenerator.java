package apps.amaralus.qa.platform.placeholder.generate;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimestampGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return Instant.now();
    }

    @Override
    public GeneratedLocationType getPlaceholderLocation() {
        return GeneratedLocationType.TIMESTAMP;
    }
}
