package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.environment.api.Environment;
import apps.amaralus.qa.platform.environment.database.EnvironmentModel;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService extends DatasetLinkedService<Environment, EnvironmentModel, Long> {
}
