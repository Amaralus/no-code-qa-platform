package apps.amaralus.qa.platform.rocksdb.sequence;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@Slf4j
public class AutoIncrementSequenceAspect {

    private final SequenceGenerator sequenceGenerator;
    private final MappingContext<?, ?> mappingContext;
    private final Set<Class<?>> sequences;

    public AutoIncrementSequenceAspect(SequenceGenerator sequenceGenerator, MappingContext<?, ?> mappingContext) {
        this.sequenceGenerator = sequenceGenerator;
        this.mappingContext = mappingContext;
        sequences = sequenceGenerator.getSequencesClasses();
    }

    @Around(value = "execution(public * org.springframework.data.keyvalue.repository.KeyValueRepository.save(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var entity = joinPoint.getArgs()[0];

        if (sequences.contains(entity.getClass())) {
            var persistentEntity = mappingContext.getRequiredPersistentEntity(entity.getClass());
            for (var persistentProperty : persistentEntity.getPersistentProperties(GeneratedSequence.class))
                incrementSequence(persistentEntity.getPropertyAccessor(entity), persistentProperty);
        }

        return joinPoint.proceed(new Object[]{entity});
    }

    @SuppressWarnings("java:S2589")
    private void incrementSequence(PersistentPropertyAccessor<?> accessor, PersistentProperty<?> persistentProperty) {
        var propertyValue = accessor.getProperty(persistentProperty);
        // null or int or long equality
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

    private String getSequenceName(PersistentProperty<?> persistentProperty) {
        var name = persistentProperty.getRequiredAnnotation(GeneratedSequence.class).value();
        if (name.isEmpty())
            name = persistentProperty.getOwner().getType().getName() + "." + persistentProperty.getName();
        return name;
    }
}