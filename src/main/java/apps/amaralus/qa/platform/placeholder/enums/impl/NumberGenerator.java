package apps.amaralus.qa.platform.placeholder.enums.impl;

import apps.amaralus.qa.platform.placeholder.enums.PlaceholderGenerator;
import org.apache.commons.lang3.RandomUtils;

public class NumberGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return RandomUtils.nextLong();
    }
}
