package apps.amaralus.qa.platform.dataset.model.api;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.model.Backlink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dataset implements IdSource<Long> {

    private Long id = 0L;
    private String name;
    private String description;
    private boolean linked;
    private Backlink<?, ?> backlink;
    private Map<String, Object> variables;

    public Dataset(String name, String description, boolean linked, Backlink<?, ?> backlink) {
        this(0L, name, description, linked, backlink, new HashMap<>());
    }

    public void addVariable(String key, Object value) {
        variables.put(key, value);
    }

    public Object getVariable(String key) {
        return variables.get(key);
    }
}
