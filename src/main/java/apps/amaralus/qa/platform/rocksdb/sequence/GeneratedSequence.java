package apps.amaralus.qa.platform.rocksdb.sequence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = FIELD)
public @interface GeneratedSequence {

    String value() default "";
}
