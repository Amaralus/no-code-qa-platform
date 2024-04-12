package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AliasService extends ProjectLinkedService<Alias, AliasModel, Long> {

    public Optional<Alias> findByName(@NotNull String name) {
        return findModelByName(name)
                .map(mapper::toEntity);
    }

    public Optional<AliasModel> findModelByName(@NotNull String name) {
        Assert.notNull(name, "name must not be null!");
        return repository().findByNameAndProject(name, projectContext.getProjectId());
    }

    private AliasRepository repository() {
        return (AliasRepository) repository;
    }
}
