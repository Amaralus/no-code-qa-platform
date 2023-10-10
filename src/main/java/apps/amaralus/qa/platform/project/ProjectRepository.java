package apps.amaralus.qa.platform.project;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ProjectRepository extends KeyValueRepository<Project, String> {
}
