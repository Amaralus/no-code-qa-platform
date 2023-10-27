package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;

    public void deleteAllByProject(String project) {
        environmentRepository.deleteAllByProject(project);
    }

    public EnvironmentModel createEnvironment(Environment environment) {
        projectRepository.findById(environment.project())
                .orElseThrow(() -> new EntityNotFoundException(ProjectModel.class.getName(), environment.project()));

        return environmentRepository.save(new EnvironmentModel(environment.name(), environment.description(), environment.project()));
    }

    public EnvironmentModel updateEnvironment(Long id, Environment environment) {

        EnvironmentModel environmentModel = environmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EnvironmentModel.class.getName(), id.toString()));

        environmentModel.setName(environment.name());
        environmentModel.setDescription(environment.description());

       return environmentRepository.save(environmentModel);
    }

    public void deleteEnvironment(Long id) {
        environmentRepository.deleteById(id);
    }
}
