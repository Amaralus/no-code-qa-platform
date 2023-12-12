package apps.amaralus.qa.platform.placeholder.enums.impl;

import apps.amaralus.qa.platform.placeholder.enums.PlaceholderGenerator;

import java.time.Instant;

public class TimestampGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return Instant.now();
    }
}
