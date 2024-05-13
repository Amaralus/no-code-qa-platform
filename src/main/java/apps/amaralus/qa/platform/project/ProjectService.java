package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.common.CrudService;
import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.model.Backlink;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.project.api.Project;
import apps.amaralus.qa.platform.project.database.ProjectModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService extends CrudService<Project, ProjectModel, String> {

    private final FolderService folderService;
    private final DatasetService datasetService;
    private final List<? extends ProjectLinkedService<?, ?, ?>> projectLinked;

    @Override
    protected void beforeCreate(ProjectModel model) {
        var dataset = datasetService.create(
                new Dataset("System dataset for Project#" + model.getId(), null, true, new Backlink<>(modelClass, model.getId())));
        model.setDataset(dataset.getId());
        var rootFolder = folderService.createProjectRoot(model.getId());
        model.setRootFolder(rootFolder.getId());
    }

    public @NotNull Project updateDescription(@NotNull String id, @Nullable String description) {
        return findModifySave(id, project -> project.setDescription(description));
    }

    public @NotNull Project updateName(@NotNull String id, @NotNull String name) {
        Assert.notNull(name, "name must not be null!");
        return findModifySave(id, project -> project.setName(name));
    }

    @Override
    public void delete(@NotNull String id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        log.info("удаляю все для проекта {}", id);
        if (!repository.existsById(id))
            return;

        log.info("удаляю все для проекта {}", id);
        projectLinked.forEach(ProjectLinkedService::deleteAllByProject);

        repository.deleteById(id);
    }
}
