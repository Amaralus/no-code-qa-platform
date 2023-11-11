package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.environment.EnvironmentService;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.label.LabelService;
import apps.amaralus.qa.platform.mapper.project.ProjectMapper;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
import apps.amaralus.qa.platform.service.ITServiceService;
import apps.amaralus.qa.platform.testcase.TestCaseService;
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
public class ProjectService {
    private static final String ID_NOT_NULL_MESSAGE = "id must not be null!";
    private static final String PROJECT_TEXT = "Project [";
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final FolderService folderService;
    private final TestCaseService testCaseService;
    private final LabelService labelService;
    private final EnvironmentService environmentService;
    private final ITServiceService itService;
    private final DatasetService datasetService;

    public @NotNull ProjectModel create(@NotNull Project project) {
        Assert.notNull(project, "project must not be null!");

        if (projectRepository.existsById(project.id()))
            throw new IllegalArgumentException(PROJECT_TEXT + project.id() + "] already exists!");

        var rootFolder = folderService.createProjectRoot(project.id());
        ProjectModel projectModel = projectMapper.mapToM(project);
        projectModel.setRootFolder(rootFolder.getId());

        return projectRepository.save(projectModel);
    }

    public @NotNull ProjectModel updateDescription(@NotNull String id, @Nullable String description) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        var project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(PROJECT_TEXT + id + "] not exists!"));

        project.setDescription(description);

        return projectRepository.save(project);
    }

    public @NotNull ProjectModel updateName(@NotNull String id, @NotNull String name) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(name, "name must not be null!");

        var project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(PROJECT_TEXT + id + "] not exists!"));

        project.setName(name);

        return projectRepository.save(project);
    }

    public void delete(@NotNull String id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        if (!projectRepository.existsById(id))
            return;

        // возможно стоит ввести общий интерфейс для операций с привязкой к проекту
        labelService.deleteAllByProject(id);
        testCaseService.deleteAllByProject(id);
        folderService.deleteAllByProject(id);
        environmentService.deleteAllByProject(id);
        itService.deleteAllByProject(id);
        datasetService.deleteAllByProject(id);

        projectRepository.deleteById(id);
    }

    public List<ProjectModel> findAll() {
        return projectRepository.findAll();
    }
}
