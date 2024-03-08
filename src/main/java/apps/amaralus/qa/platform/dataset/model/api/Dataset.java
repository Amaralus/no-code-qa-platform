package apps.amaralus.qa.platform.dataset.model.api;

import java.util.HashMap;
import java.util.Map;

public record Dataset(
        long id,
        String name,
        String description,
        boolean linkedToEntity,
        Map<String, Object> variables
) {

    public Dataset(String name, String description, boolean linkedToEntity) {
        this(0, name, description, linkedToEntity, new HashMap<>());
    }

    public void addVariable(String key, Object value) {
        variables.put(key, value);
    }

    public Object getVariable(String key) {
        return variables.get(key);
    }
}
