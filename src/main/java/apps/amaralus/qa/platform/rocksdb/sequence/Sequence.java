package apps.amaralus.qa.platform.rocksdb.sequence;

import org.springframework.data.annotation.Id;

public record Sequence(@Id String name, Class<?> sequenceClass, long value) {
    public static String KEY_SPACE = "sequence";

    public Sequence(String name, Class<?> sequenceClass) {
        this(name, sequenceClass, 0);
    }
}
