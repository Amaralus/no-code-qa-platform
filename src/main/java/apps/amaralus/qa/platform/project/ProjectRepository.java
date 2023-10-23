package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.project.model.ProjectModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ProjectRepository extends KeyValueRepository<ProjectModel, String> {
}
