package apps.amaralus.qa.platform.placeholder.enums.impl;

import apps.amaralus.qa.platform.placeholder.enums.PlaceholderGenerator;
import org.apache.commons.lang3.RandomStringUtils;

public class StringGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return RandomStringUtils.randomAlphabetic(5);
    }
}
