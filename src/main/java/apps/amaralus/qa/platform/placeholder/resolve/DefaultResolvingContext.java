package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.common.BaseRepository;
import apps.amaralus.qa.platform.common.model.DatasetSourceModel;
import apps.amaralus.qa.platform.dataset.DatasetRepository;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.folder.FolderRepository;
import apps.amaralus.qa.platform.placeholder.PlaceholderType;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType.*;

@Component
@RequiredArgsConstructor
public class DefaultResolvingContext implements ResolvingContext {

    private final DatasetRepository datasetRepository;
    private final FolderRepository folderRepository;
    private final TestCaseRepository testCaseRepository;
    private ProjectContext projectContext;

    @Override
    public Optional<DatasetModel> findDataset(PlaceholderType placeholderType, Long id) {

        if (placeholderType == DATASET) {
            return datasetRepository.findByIdAndProject(id, projectContext.getProjectId());
        } else if (placeholderType == FOLDER) {
            return findDatasetById(folderRepository, id);
        } else if (placeholderType == TESTCASE) {
            return findDatasetById(testCaseRepository, id);
        } else
            return Optional.empty();
    }

    private <T extends DatasetSourceModel<I>, I> Optional<DatasetModel> findDatasetById(
            BaseRepository<T, I> repository, I id) {
        return repository.findByIdAndProject(id, projectContext.getProjectId())
                .map(model -> datasetRepository.findByIdAndProject(model.getDataset(), model.getProject()).get());
    }

    @Autowired
    @Override
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }
}
