package apps.amaralus.qa.platform.service.model;

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
public class ITServiceModel {
        @Id
        @GeneratedSequence("it-service-id")
        private long id;
        private String name;
        private String description;
        private String project;
        private List<EnvironmentModel> environments;
        private long dataset;

        public ITServiceModel(String name, String description, String project) {
                this.name = name;
                this.description = description;
                this.project = project;
        }

        public ITServiceModel(String name, String description, String project, List<EnvironmentModel> environments, long dataset) {
                this.name = name;
                this.description = description;
                this.project = project;
                this.environments = environments;
                this.dataset = dataset;
        }
}
