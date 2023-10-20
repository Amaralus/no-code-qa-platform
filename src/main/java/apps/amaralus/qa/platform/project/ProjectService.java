package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.label.LabelService;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FolderService folderService;
    private final TestCaseService testCaseService;
    private final LabelService labelService;

    public @NotNull ProjectModel create(@NotNull Project project) {
        Assert.notNull(project, "project must not be null!");

        if (projectRepository.existsById(project.name()))
            throw new IllegalArgumentException("Project [" + project.name() + "] already exists!");

        var rootFolder = folderService.createProjectRoot(project.name());

        return projectRepository.save(new ProjectModel(project.name(), project.description(), rootFolder.getId()));
    }

    public @NotNull ProjectModel updateDescription(@NotNull String projectName, @NotNull String description) {
        Assert.notNull(projectName, "projectName must not be null!");
        Assert.notNull(description, "description must not be null!");

        var project = projectRepository.findById(projectName)
                .orElseThrow(() -> new IllegalArgumentException("Project [" + projectName + "] not exists!"));

        return projectRepository.save(new ProjectModel(project.name(), description, project.rootFolder()));
    }

    public void delete(@NotNull String projectName) {
        Assert.notNull(projectName, "projectName must not be null!");

        if (!projectRepository.existsById(projectName))
            return;

        // возможно стоит ввести общий интерфейс для операций с привязкой к проекту
        labelService.deleteAllByProject(projectName);
        testCaseService.deleteAllByProject(projectName);
        folderService.deleteAllByProject(projectName);
    }

    public List<ProjectModel> findAll() {
        return projectRepository.findAll();
    }
}