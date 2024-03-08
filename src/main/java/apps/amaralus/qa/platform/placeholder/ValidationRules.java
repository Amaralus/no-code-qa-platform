package apps.amaralus.qa.platform.placeholder;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

import static apps.amaralus.qa.platform.placeholder.ValidationRules.Rule.EXCLUDE;
import static apps.amaralus.qa.platform.placeholder.ValidationRules.Rule.INCLUDE;

public record ValidationRules(Rule id, Rule variable) {
    public static final ValidationRules LOCATION_ONLY = new ValidationRules(EXCLUDE, EXCLUDE);
    public static final ValidationRules FULL_PATH = new ValidationRules(INCLUDE, INCLUDE);

    public boolean validate(Long id, String variable) {
        return this.id.validate(id) && this.variable.validate(variable);
    }

    @RequiredArgsConstructor
    public enum Rule {
        INCLUDE(Objects::nonNull),
        OPTIONAL(object -> true),
        EXCLUDE(Objects::isNull);

        private final Function<Object, Boolean> validationFunction;

        public boolean validate(Object object) {
            return validationFunction.apply(object);
        }
    }
}
