package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvironmentService extends ProjectLinked {
    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;
    private final EnvironmentMapper environmentMapper;

    public List<Environment> findAllByProject(String project) {
        return environmentMapper.toEntityList(environmentRepository.findAllByProject(project));
    }

    @Override
    public void deleteAllByProject() {
        environmentRepository.deleteAll(environmentRepository.findAllByProject(projectContext.getProjectId()));
    }

    public Environment createEnvironment(Environment environment) {

        if (!projectRepository.existsById(environment.project())) {
            throw new EntityNotFoundException(ProjectModel.class, environment.project());
        }

        var environmentModel = environmentMapper.toModel(environment);
        return environmentMapper.toEntity(environmentRepository.save(environmentModel));
    }

    public Environment updateEnvironment(Long id, Environment environment) {

        var environmentModel = environmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EnvironmentModel.class, id));


        var updated = environmentMapper.update(environmentModel, environment);

        return environmentMapper.toEntity(environmentRepository.save(updated));
    }

    public void deleteEnvironment(Long id) {
        environmentRepository.deleteById(id);
    }
}
