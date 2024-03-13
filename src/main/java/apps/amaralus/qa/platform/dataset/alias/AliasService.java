package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.common.exception.EntityAlreadyExistsException;
import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AliasService extends ProjectLinked {

    private final AliasRepository aliasRepository;
    private final AliasMapper aliasMapper;

    public Alias save(Alias alias) {
        if (aliasRepository.existsById(alias.id()))
            throw new EntityAlreadyExistsException(alias.id());

        var aliasModel = aliasMapper.toModel(alias);
        aliasModel.setProject(projectContext.getProjectId());
        return aliasMapper.toEntity(aliasRepository.save(aliasModel));
    }

    public void deleteAliasByName(String name) {
        aliasRepository.deleteAll(aliasRepository.findAllByNameAndProject(name, projectContext.getProjectId()));
    }

    @Override
    public void deleteAllByProject() {
        aliasRepository.deleteAll(aliasRepository.findAllByProject(projectContext.getProjectId()));
    }

    public Optional<Alias> getAliasByName(String aliasName) {
        return aliasRepository.findByNameAndProject(aliasName, projectContext.getProjectId())
                .map(aliasMapper::toEntity);
    }
}
