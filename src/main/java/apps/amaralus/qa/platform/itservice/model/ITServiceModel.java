package apps.amaralus.qa.platform.itservice.model;

import apps.amaralus.qa.platform.common.model.CrudModel;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.List;

@Data
@NoArgsConstructor
@KeySpace("it-service")
public class ITServiceModel implements CrudModel<Long> {
        @Id
        @GeneratedSequence("it-service-id")
        private Long id;
        private String name;
        private String description;
        private String project;
        private List<EnvironmentModel> environments;
        private long dataset;
}
