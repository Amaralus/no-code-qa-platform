package apps.amaralus.qa.platform.itservice;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.itservice.model.ITServiceModel;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ITServiceService extends ProjectLinked {
    private final ITServiceRepository itServiceRepository;
    private final ProjectRepository projectRepository;
    private final ITServiceMapper itServiceMapper;

    public List<ITService> findAllByProject(String project) {
        return itServiceMapper.toEntityList(itServiceRepository.findAllByProject(project));
    }

    @Override
    public void deleteAllByProject() {
        itServiceRepository.deleteAll(itServiceRepository.findAllByProject(projectContext.getProjectId()));
    }

    public ITService createService(ITService itService) {

        if (!projectRepository.existsById(itService.project())) {
            throw new EntityNotFoundException(ProjectModel.class, itService.project());
        }

        var itServiceModel = itServiceMapper.toModel(itService);

        return itServiceMapper.toEntity(itServiceRepository.save(itServiceModel));
    }

    public ITService updateService(Long id, ITService itService) {

        var itServiceModel = itServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ITServiceModel.class, id));

        var updated = itServiceMapper.update(itServiceModel, itService);

        return itServiceMapper.toEntity(itServiceRepository.save(updated));
    }

    public void deleteService(Long id) {
        itServiceRepository.deleteById(id);
    }
}
