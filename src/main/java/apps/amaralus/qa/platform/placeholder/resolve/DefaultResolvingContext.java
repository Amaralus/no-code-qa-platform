package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.common.BaseRepository;
import apps.amaralus.qa.platform.common.model.DatasetSourceModel;
import apps.amaralus.qa.platform.dataset.DatasetRepository;
import apps.amaralus.qa.platform.dataset.alias.AliasRepository;
import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.folder.FolderRepository;
import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.project.ProjectRepository;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultResolvingContext implements ResolvingContext {

    private final DatasetRepository datasetRepository;
    private final FolderRepository folderRepository;
    private final TestCaseRepository testCaseRepository;
    private final ProjectRepository projectRepository;
    private final AliasRepository aliasRepository;
    private ProjectContext projectContext;

    @Override
    public Optional<DatasetModel> findDataset(DefaultPlaceholderType placeholderType, Long id) {

        return switch (placeholderType) {
            case DATASET -> findDataset(id);
            case FOLDER -> findLinkedDataset(folderRepository, id);
            case TESTCASE -> findLinkedDataset(testCaseRepository, id);
            // todo доделать
            case SERVICE, ENVIRONMENT -> Optional.empty();
            case PROJECT -> projectRepository.findById(projectContext.getProjectId())
                    .flatMap(model -> findDataset(model.getDataset()));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<DatasetModel> findDataset(Long id) {
        return datasetRepository.findByIdAndProject(id, projectContext.getProjectId());
    }

    @Override
    public Optional<AliasModel> findAlias(String name) {
        return aliasRepository.findByNameAndProject(name, projectContext.getProjectId());
    }

    private <T extends DatasetSourceModel<I>, I> Optional<DatasetModel> findLinkedDataset(
            BaseRepository<T, I> repository,
            I id) {
        return repository.findByIdAndProject(id, projectContext.getProjectId())
                .flatMap(model -> datasetRepository.findByIdAndProject(model.getDataset(), model.getProject()));
    }

    @Autowired
    @Override
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }
}
