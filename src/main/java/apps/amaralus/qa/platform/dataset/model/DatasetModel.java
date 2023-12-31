package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.Map;

@Data
@KeySpace("dataset")
public class DatasetModel {
    @Id
    @GeneratedSequence("dataset-id")
    private long id;
    private String name;
    private String path;
    private String description;
    private String project;
    private Map<String, Object> variables;
}
