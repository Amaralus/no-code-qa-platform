package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.common.CrudService;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
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
    private static final String PROJECT_TEXT = "Project [";
    private final FolderService folderService;
    private final List<? extends ProjectLinkedService> projectLinked;

    @Override
    protected void beforeCreate(ProjectModel model) {
        var rootFolder = folderService.createProjectRoot(model.getId());
        model.setRootFolder(rootFolder.getId());
    }

    public @NotNull Project updateDescription(@NotNull String id, @Nullable String description) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        var project = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(PROJECT_TEXT + id + "] not exists!"));

        project.setDescription(description);

        return mapper.toEntity(repository.save(project));
    }

    public @NotNull Project updateName(@NotNull String id, @NotNull String name) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(name, "name must not be null!");

        var project = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(PROJECT_TEXT + id + "] not exists!"));

        project.setName(name);

        return mapper.toEntity(repository.save(project));
    }

    @Override
    public void delete(@NotNull String id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        if (!repository.existsById(id))
            return;

        projectLinked.forEach(ProjectLinkedService::deleteAllByProject);

        repository.deleteById(id);
    }
}
