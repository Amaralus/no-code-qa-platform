package apps.amaralus.qa.platform.placeholder.enums;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Supplier;

public enum Placeholder {

    UUID {
        @Override
        public Supplier<Object> generateValue() {
            return java.util.UUID::randomUUID;
        }
    },
    TIMESTAMP {
        @Override
        public Supplier<Object> generateValue() {
            return Instant::now;
        }
    },
    WORD {
        @Override
        public Supplier<Object> generateValue() {
            return () -> RandomStringUtils.randomAlphabetic(5);
        }
    },
    NUMBER {
        @Override
        public Supplier<Object> generateValue() {
            return RandomUtils::nextLong;
        }
    };

    public abstract Supplier<Object> generateValue();

    public static Optional<Object> getValueByPlaceholder(String name) {
        return EnumSet.allOf(Placeholder.class).stream()
                .filter(placeholder -> placeholder.name().equalsIgnoreCase(name))
                .findFirst()
                .map(placeholder -> placeholder.generateValue().get());
    }
}
