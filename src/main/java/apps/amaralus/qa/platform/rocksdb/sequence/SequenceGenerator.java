package apps.amaralus.qa.platform.rocksdb.sequence;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SequenceGenerator {

    private final Map<String, Sequence> sequences;
    private final KeyValueAdapter keyValueAdapter;

    public SequenceGenerator(MappingContext<BasicPersistentEntity<?, ?>, ?> mappingContext,
                             KeyValueAdapter keyValueAdapter) {
        this.keyValueAdapter = keyValueAdapter;

        var scannedSequences = new HashMap<>(createSequences(mappingContext));
        var existedSequences = loadSequences();

        for (var existed : existedSequences.entrySet()) {
            if (!scannedSequences.containsKey(existed.getKey()))
                // deleting sequences that no longer exist
                keyValueAdapter.delete(existed.getKey(), Sequence.KEY_SPACE);
            else
                // setup existing values
                scannedSequences.merge(
                        existed.getKey(),
                        existed.getValue(),
                        (scannedSequence, existedSequence) -> new Sequence(
                                scannedSequence.name(),
                                // // put a new class if sequence class has changed
                                scannedSequence.sequenceClass(),
                                existedSequence.value()));
        }

        sequences = new ConcurrentHashMap<>(scannedSequences);
    }

    public long increment(@NotNull String sequenceName) {
        Assert.notNull(sequenceName, "Sequence name must not be null!");

        var incrementedSequence = sequences.computeIfPresent(sequenceName, (name, sequence) -> {
            var increment = new Sequence(sequenceName, sequence.sequenceClass(), sequence.value() + 1);
            keyValueAdapter.put(sequence, increment, Sequence.KEY_SPACE);
            return increment;
        });

        if (incrementedSequence == null)
            throw new IllegalStateException("Sequence [" + sequenceName + "] doesn't exist!");

        return incrementedSequence.value();
    }

    private Map<String, Sequence> loadSequences() {
        var map = new HashMap<String, Sequence>();
        for (var sequence : keyValueAdapter.getAllOf(Sequence.KEY_SPACE, Sequence.class))
            map.put(sequence.name(), sequence);
        return map;
    }

    private Map<String, Sequence> createSequences(MappingContext<BasicPersistentEntity<?, ?>, ?> mappingContext) {
        return mappingContext.getPersistentEntities().stream()
                .flatMap(persistentEntity -> ((List<?>) persistentEntity.getPersistentProperties(GeneratedSequence.class)).stream())
                .map(PersistentProperty.class::cast)
                .map(this::toSequence)
                .collect(Collectors.toMap(
                        Sequence::name,
                        Function.identity()
                ));
    }

    private Sequence toSequence(PersistentProperty<?> persistentProperty) {
        var sequenceClass = persistentProperty.getOwner().getType();
        var sequenceName = persistentProperty.getRequiredAnnotation(GeneratedSequence.class).value();
        if (sequenceName.isEmpty())
            sequenceName = sequenceClass.getName() + "." + persistentProperty.getName();

        return new Sequence(sequenceName, sequenceClass);
    }
}
