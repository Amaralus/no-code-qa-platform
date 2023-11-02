package apps.amaralus.qa.platform.service;

import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.service.model.ITServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ITServiceService {
    private final ITServiceRepository itServiceRepository;
    private final ProjectRepository projectRepository;

    public void deleteAllByProject(String project) {
        itServiceRepository.deleteAllByProject(project);
    }

    public ITServiceModel createService(ITService itService) {

        projectRepository.findById(itService.project())
                .orElseThrow(() -> new EntityNotFoundException(ProjectModel.class.getName(), itService.project()));

        return itServiceRepository.save(new ITServiceModel(itService.name(), itService.description(), itService.project()));
    }

    public ITServiceModel updateService(Long id, ITService itService) {

        ITServiceModel itServiceModel = itServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ITServiceModel.class.getName(), id.toString()));

        itServiceModel.setName(itService.name());
        itServiceModel.setDescription(itService.description());

        return itServiceRepository.save(itServiceModel);
    }

    public void deleteService(Long id) {
        itServiceRepository.deleteById(id);
    }
}
