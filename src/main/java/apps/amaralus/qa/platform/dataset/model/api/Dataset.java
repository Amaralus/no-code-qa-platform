package apps.amaralus.qa.platform.dataset.model.api;

import apps.amaralus.qa.platform.common.model.IdSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dataset implements IdSource<Long> {

    private Long id;
    private String name;
    private String description;
    private boolean linked;
    private Map<String, Object> variables;

    public Dataset(String name, String description, boolean linked) {
        this(0L, name, description, linked, new HashMap<>());
    }

    public void addVariable(String key, Object value) {
        variables.put(key, value);
    }

    public Object getVariable(String key) {
        return variables.get(key);
    }
}
