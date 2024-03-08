package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AliasService extends ProjectLinked {

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
        aliasRepository.deleteAll(aliasRepository.findAllByNameAndProject(name, project));
    }

    @Override
    public void deleteAllByProject() {
        aliasRepository.deleteAll(aliasRepository.findAllByProject(projectContext.getProjectId()));
    }

    public Optional<Alias> getAliasByName(String aliasName, String project) {
        return aliasRepository.findByNameAndProject(aliasName, project)
                .map(aliasMapper::mapToD);
    }
}
