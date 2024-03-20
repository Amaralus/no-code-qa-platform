package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService extends DatasetLinkedService<Environment, EnvironmentModel, Long> {
}
