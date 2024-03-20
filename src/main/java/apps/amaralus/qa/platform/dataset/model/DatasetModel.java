package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.HashMap;
import java.util.Map;

@Data
@KeySpace("dataset")
public class DatasetModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("dataset-id")
    private Long id;
    private String name;
    private String description;
    private boolean linked = false;
    private String project;
    private Map<String, Object> variables = new HashMap<>();

    public Object getVariable(String key) {
        return variables.get(key);
    }
}
