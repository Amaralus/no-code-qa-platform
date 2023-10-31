package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface EnvironmentRepository extends KeyValueRepository<EnvironmentModel, Long> {
    void deleteAllByProject(String project);
}
