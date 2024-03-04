package apps.amaralus.qa.platform.placeholder.generate;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class NumberGenerator implements PlaceholderGenerator {
    @Override
    public Object generateValue() {
        return RandomUtils.nextLong();
    }

    @Override
    public GeneratedPlaceholderType getPlaceholderLocation() {
        return GeneratedPlaceholderType.NUMBER;
    }
}
