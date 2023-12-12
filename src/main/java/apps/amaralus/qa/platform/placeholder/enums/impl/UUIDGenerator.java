package apps.amaralus.qa.platform.placeholder.enums.impl;

import apps.amaralus.qa.platform.placeholder.enums.PlaceholderGenerator;

import java.util.UUID;

public class UUIDGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return UUID.randomUUID();
    }
}
