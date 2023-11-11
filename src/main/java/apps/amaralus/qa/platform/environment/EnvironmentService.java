package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.environment.mapper.EnvironmentMapper;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;
    private final EnvironmentMapper environmentMapper;

    public List<Environment> findAllByProject(String project) {
        return environmentMapper.toEnvironments(environmentRepository.findAllByProject(project));
    }

    public void deleteAllByProject(String project) {
        environmentRepository.deleteAll(environmentRepository.findAllByProject(project));
    }

    public Environment createEnvironment(Environment environment) {

        projectRepository.findById(environment.project())
                .orElseThrow(() -> new EntityNotFoundException(ProjectModel.class.getName(), environment.project()));

        EnvironmentModel environmentModel = environmentMapper.toEnvironmentModel(environment);
        return environmentMapper.toEnvironment(environmentRepository.save(environmentModel));
    }

    public Environment updateEnvironment(Long id, Environment environment) {

        EnvironmentModel environmentModel = environmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EnvironmentModel.class.getName(), id.toString()));


        EnvironmentModel updated = environmentMapper.update(environmentModel, environment);

        return environmentMapper.toEnvironment(environmentRepository.save(updated));
    }

    public void deleteEnvironment(Long id) {
        environmentRepository.deleteById(id);
    }
}
