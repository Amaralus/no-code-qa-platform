package apps.amaralus.qa.platform.placeholder.generate;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class StringGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    @Override
    public GeneratedLocationType getPlaceholderLocation() {
        return GeneratedLocationType.STRING;
    }
}
