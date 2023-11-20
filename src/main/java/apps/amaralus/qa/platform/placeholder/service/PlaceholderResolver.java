package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.placeholder.enums.Placeholder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class PlaceholderResolver {

    public String getPropertyValue(String key, Map<String, Object> properties) {
        return this.getPropertyValue(key, properties, new CircularDefinitionProtector());
    }

    private String getPropertyValue(String key, Map<String, Object> properties, CircularDefinitionProtector circularDefinitionProtector) {

        if (circularDefinitionProtector.isPropertyAlreadyVisited(key)) {
            circularDefinitionProtector.throwCircularDefinitionException();
        }


        String rawValue = getRawString(key, properties);

        if (StringUtils.isEmpty(rawValue)) {
            return null;
        }

        ExpansionBuffer buffer = new ExpansionBuffer(rawValue);
        String newKey;

        while ((newKey = buffer.extractNextPropertyKey()) != null) {
            buffer.moveResolvedPartToNextProperty();
            String newValue = getPropertyValue(newKey, properties, circularDefinitionProtector.cloneWithAdditionalKey(key));
            if (newValue == null) {
                buffer.add("{{" + newKey + "}}");
            } else {
                buffer.add(newValue);
            }
        }

        return buffer.getFullyResolved();
    }

    private String getRawString(String key, Map<String, Object> properties) {
        Object o = ObjectUtils.defaultIfNull(
                properties.get(key),
                Placeholder.getValueByPlaceholder(key)
        );
        return Objects.nonNull(o) ? o.toString() : null;
    }
}
