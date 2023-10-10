package apps.amaralus.qa.platform.rocksdb.sequence;

import apps.amaralus.qa.platform.rocksdb.RocksDbKeyValueAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.InvalidPropertyException;
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

    private final Map<String, Sequence> sequences = new ConcurrentHashMap<>();
    private final MappingContext<BasicPersistentEntity<?, ?>, ?> mappingContext;
    private final RocksDbKeyValueAdapter keyValueAdapter;

    public SequenceGenerator(MappingContext<BasicPersistentEntity<?, ?>, ?> mappingContext,
                             RocksDbKeyValueAdapter keyValueAdapter) {
        this.mappingContext = mappingContext;
        this.keyValueAdapter = keyValueAdapter;
        initializeSequences();
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

    private void initializeSequences() {
        var scannedSequences = createSequences();
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

        sequences.putAll(scannedSequences);
        sequences.values().forEach(sequence -> keyValueAdapter.put(sequence.name(), sequence, Sequence.KEY_SPACE));
    }

    private Map<String, Sequence> loadSequences() {
        var map = new HashMap<String, Sequence>();
        for (var sequence : keyValueAdapter.getAllOf(Sequence.KEY_SPACE, Sequence.class))
            map.put(sequence.name(), sequence);
        return map;
    }

    private Map<String, Sequence> createSequences() {
        Map<String, Sequence> map = keyValueAdapter.getKeySpacesEntityClasses().values().stream()
                .filter(clazz -> !clazz.isAssignableFrom(Sequence.class))
                // forced initialization of mapping context (by default mapping context has lazy initialization)
                .map(mappingContext::getRequiredPersistentEntity)
                .flatMap(persistentEntity -> ((List<?>) persistentEntity.getPersistentProperties(GeneratedSequence.class)).stream())
                .map(PersistentProperty.class::cast)
                .map(this::toSequence)
                .collect(Collectors.toMap(
                        Sequence::name,
                        Function.identity()
                ));
        // make mutable
        return new HashMap<>(map);
    }

    private Sequence toSequence(PersistentProperty<?> persistentProperty) {
        validateProperty(persistentProperty);

        var sequenceClass = persistentProperty.getOwner().getType();

        var sequenceName = persistentProperty.getRequiredAnnotation(GeneratedSequence.class).value();
        if (sequenceName.isEmpty())
            sequenceName = sequenceClass.getName() + "." + persistentProperty.getName();

        return new Sequence(sequenceName, sequenceClass);
    }

    private void validateProperty(PersistentProperty<?> persistentProperty) {
        var sequenceClass = persistentProperty.getOwner().getType();
        if (sequenceClass.isRecord())
            throw new InvalidPropertyException(sequenceClass, persistentProperty.getName(), "Sequences in records are not supported");

        var propertyType = persistentProperty.getType();

        if (!propertyType.equals(Long.class)
                && !propertyType.equals(Long.TYPE)
                && !propertyType.equals(Integer.class)
                && !propertyType.equals(Integer.TYPE))
            throw new InvalidPropertyException(sequenceClass, persistentProperty.getName(), "Sequence only supports long or integer types");
    }
}
