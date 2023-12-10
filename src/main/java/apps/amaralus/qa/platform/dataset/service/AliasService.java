package apps.amaralus.qa.platform.dataset.service;

import apps.amaralus.qa.platform.dataset.dto.Alias;
import apps.amaralus.qa.platform.dataset.model.AliasModel;
import apps.amaralus.qa.platform.dataset.repository.AliasRepository;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.mapper.dataset.AliasMapper;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AliasService {

    private final AliasRepository aliasRepository;
    private final ProjectRepository projectRepository;
    private final AliasMapper aliasMapper;

    public Alias save(Alias alias) {

        if (!projectRepository.existsById(alias.project())) {
            throw new EntityNotFoundException(ProjectModel.class, alias.project());
        }

        var aliasModel = aliasMapper.mapToM(alias);
        return aliasMapper.mapToD(aliasRepository.save(aliasModel));
    }

    public Alias updateAliasName(String newName, String oldName, String project) {

        var alias = aliasRepository.findByNameAndProject(oldName, project)
                .map(aliasModel -> {
                    aliasModel.setName(newName);
                    return aliasRepository.save(aliasModel);
                })
                .orElseThrow(() -> new EntityNotFoundException(AliasModel.class));

        return aliasMapper.mapToD(alias);
    }

    public void deleteAliasByName(String name, String project) {
        aliasRepository.deleteByNameAndProject(name, project);
    }

    public void deleteAllByProject(String project) {
        aliasRepository.deleteAll(aliasRepository.findAllByProject(project));
    }

    public Alias getAliasByName(String aliasName, String project) {
        return aliasRepository.findByNameAndProject(aliasName, project)
                .map(aliasMapper::mapToD)
                .orElse(null);
    }
}
