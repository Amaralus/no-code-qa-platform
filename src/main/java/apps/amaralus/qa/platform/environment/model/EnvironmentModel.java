package apps.amaralus.qa.platform.environment.model;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@NoArgsConstructor
@KeySpace("environment")
public class EnvironmentModel {

    @Id
    @GeneratedSequence("environment-id")
    private long id;
    private String name;
    private String description;
    private String project;
    private long dataset;

    public EnvironmentModel(String name, String description, String project) {
        this.name = name;
        this.description = description;
        this.project = project;
    }
}
