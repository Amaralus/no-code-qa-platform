package apps.amaralus.qa.platform.environment.database;

import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@NoArgsConstructor
@KeySpace("environment")
public class EnvironmentModel implements ProjectLinkedModel<Long>, DatasetSource {

    @Id
    @GeneratedSequence("environment-id")
    private Long id;
    private String name;
    private String description;
    private String project;
    private long dataset;
}
