package apps.amaralus.qa.platform.rocksdb.sequence;

import apps.amaralus.qa.platform.rocksdb.key.CompoundKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class AutoIncrementSequenceAspect {

    private final SequenceGenerator sequenceGenerator;
    private final MappingContext<?, ?> mappingContext;
    private final Set<Class<?>> sequences;

    public AutoIncrementSequenceAspect(SequenceGenerator sequenceGenerator,
                                       MappingContext<?, ?> mappingContext) {
        this.sequenceGenerator = sequenceGenerator;
        this.mappingContext = mappingContext;
        sequences = sequenceGenerator.getSequencesClasses();
    }

    @Around(value = "execution(public * org.springframework.data.keyvalue.repository.KeyValueRepository.save(..))")
    public Object aroundSave(ProceedingJoinPoint joinPoint) throws Throwable {
        var entity = joinPoint.getArgs()[0];

        handleEntity(entity);

        return joinPoint.proceed(new Object[]{entity});
    }

    @Around(value = "execution(* org.springframework.data.repository.CrudRepository.saveAll(..))")
    public Object aroundSaveAll(ProceedingJoinPoint joinPoint) throws Throwable {
        var entities = (Iterable<?>) joinPoint.getArgs()[0];

        entities.forEach(this::handleEntity);

        return joinPoint.proceed(new Object[]{entities});
    }

    private void handleEntity(Object entity) {
        if (sequences.contains(entity.getClass())) {
            var persistentEntity = mappingContext.getRequiredPersistentEntity(entity.getClass());
            for (var persistentProperty : persistentEntity.getPersistentProperties(GeneratedSequence.class))
                incrementSequence(persistentEntity.getPropertyAccessor(entity), persistentProperty);
        }
    }

    @SneakyThrows
    @SuppressWarnings("java:S2589")
    private void incrementSequence(PersistentPropertyAccessor<?> accessor, PersistentProperty<?> persistentProperty) {
        var propertyValue = accessor.getProperty(persistentProperty);


        if (propertyValue != null && propertyValue.getClass().isAnnotationPresent(CompoundKey.class)) {
            incrementCompoundKey(persistentProperty, propertyValue);
        } else {
            if (propertyValue == null || propertyValue.equals(0) || propertyValue.equals(0L)) {
                long nextValue = sequenceGenerator.increment(getSequenceName(persistentProperty));

                // possible int casting
                if (persistentProperty.getType().equals(Integer.class)
                        || persistentProperty.getType().equals(Integer.TYPE))
                    accessor.setProperty(persistentProperty, (int) nextValue);
                else
                    accessor.setProperty(persistentProperty, nextValue);
            }
        }
    }

    @SuppressWarnings("java:S3011")
    private void incrementCompoundKey(PersistentProperty<?> persistentProperty, Object propertyValue) throws IllegalAccessException {

        for (Field field : propertyValue.getClass().getDeclaredFields()) {

            if (!field.getType().equals(Long.class)
                    && !field.getType().equals(Long.TYPE)
                    && !field.getType().equals(Integer.class)
                    && !field.getType().equals(Integer.TYPE))
                throw new InvalidPropertyException(persistentProperty.getOwner().getType(), persistentProperty.getName(), "Sequence only supports long or integer types");

            field.setAccessible(true);

            field.set(
                    propertyValue,
                    sequenceGenerator.increment(getSequenceName(persistentProperty))
            );
        }
    }

    private String getSequenceName(PersistentProperty<?> persistentProperty) {
        var name = persistentProperty.getRequiredAnnotation(GeneratedSequence.class).value();
        if (name.isEmpty())
            name = persistentProperty.getOwner().getType().getName() + "." + persistentProperty.getName();
        return name;
    }
}