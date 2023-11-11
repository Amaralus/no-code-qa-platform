package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.List;

@Data
@KeySpace("dataset")
public class DatasetModel {
    @Id
    @GeneratedSequence("dataset-id")
    private long id;
    private String name;
    private String alias;
    private String description;
    private String project;
    private List<VariableModel> variables;

    @Data
    public static class VariableModel {
        private String name;
        private String value;
    }
}
