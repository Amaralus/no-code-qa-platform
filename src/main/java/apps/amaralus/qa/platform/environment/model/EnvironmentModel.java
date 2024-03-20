package apps.amaralus.qa.platform.environment.model;

import apps.amaralus.qa.platform.common.model.CrudModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@NoArgsConstructor
@KeySpace("environment")
public class EnvironmentModel implements CrudModel<Long> {

    @Id
    @GeneratedSequence("environment-id")
    private Long id;
    private String name;
    private String description;
    private String project;
    private long dataset;
}
