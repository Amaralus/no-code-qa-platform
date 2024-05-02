package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.alias.AliasService;
import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.environment.EnvironmentService;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.project.ProjectService;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultResolvingContext implements ResolvingContext {

    private final DatasetService datasetService;
    private final FolderService folderService;
    private final TestCaseService testCaseService;
    private final ProjectService projectService;
    private final AliasService aliasService;
    private final EnvironmentService environmentService;
    private ProjectContext projectContext;

    @Override
    public Optional<DatasetModel> findDataset(DefaultPlaceholderType placeholderType, Long id) {

        return switch (placeholderType) {
            case DATASET -> findDataset(id);
            case FOLDER -> findLinkedDataset(folderService, id);
            case TESTCASE -> findLinkedDataset(testCaseService, id);
            case ENVIRONMENT -> findLinkedDataset(environmentService, id);
            // todo доделать после доработки окружения
            case SERVICE -> Optional.empty();
            case PROJECT -> projectService.findById(projectContext.getProjectId())
                    .flatMap(model -> findDataset(model.getDataset()));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<DatasetModel> findDataset(Long id) {
        return datasetService.findModelById(id);
    }

    @Override
    public Optional<AliasModel> findAlias(String name) {
        return aliasService.findModelByName(name);
    }

    private <T extends ProjectLinkedModel<I> & DatasetSource, I>
    Optional<DatasetModel> findLinkedDataset(
            ProjectLinkedService<?, T, I> service,
            I id) {
        return service.findModelById(id)
                .flatMap(model -> datasetService.findModelById(model.getDataset()));
    }

    @Autowired
    @Override
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }
}
