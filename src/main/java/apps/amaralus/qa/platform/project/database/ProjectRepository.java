package apps.amaralus.qa.platform.project.database;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ProjectRepository extends KeyValueRepository<ProjectModel, String> {
}
