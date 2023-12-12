package apps.amaralus.qa.platform.service;

import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.mapper.service.ITServiceMapper;
import apps.amaralus.qa.platform.service.model.ITServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ITServiceService {
    private final ITServiceRepository itServiceRepository;
    private final ProjectRepository projectRepository;
    private final ITServiceMapper itServiceMapper;

    public List<ITService> findAllByProject(String project) {
        return itServiceMapper.mapToListD(itServiceRepository.findAllByProject(project));
    }

    public void deleteAllByProject(String project) {
        itServiceRepository.deleteAll(itServiceRepository.findAllByProject(project));
    }

    public ITService createService(ITService itService) {

        if (!projectRepository.existsById(itService.project())) {
            throw new EntityNotFoundException(ProjectModel.class, itService.project());
        }

        var itServiceModel = itServiceMapper.mapToM(itService);

        return itServiceMapper.mapToD(itServiceRepository.save(itServiceModel));
    }

    public ITService updateService(Long id, ITService itService) {

        var itServiceModel = itServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ITServiceModel.class, id.toString()));

        var updated = itServiceMapper.update(itServiceModel, itService);

        return itServiceMapper.mapToD(itServiceRepository.save(updated));
    }

    public void deleteService(Long id) {
        itServiceRepository.deleteById(id);
    }
}
