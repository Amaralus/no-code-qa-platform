package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface EnvironmentRepository extends KeyValueRepository<EnvironmentModel, Long> {
    List<EnvironmentModel> findAllByProject(String project);
}
