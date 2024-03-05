package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.common.BaseRepository;
import apps.amaralus.qa.platform.common.DatasetSourceModel;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.repository.DatasetRepository;
import apps.amaralus.qa.platform.folder.FolderRepository;
import apps.amaralus.qa.platform.placeholder.PlaceholderType;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType.*;

@Component
@RequiredArgsConstructor
public class DefaultResolvingContext implements ResolvingContext {

    private final DatasetRepository datasetRepository;
    private final FolderRepository folderRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public Optional<DatasetModel> getDatasetById(PlaceholderType placeholderType, Long id, String project) {

        if (placeholderType == DATASET) {
            return datasetRepository.findByIdAndProject(id, project);
        } else if (placeholderType == FOLDER) {
            return findDataset(folderRepository, id, project);
        } else if (placeholderType == TESTCASE) {
            return findDataset(testCaseRepository, id, project);
        } else
            return Optional.empty();
    }

    private <T extends DatasetSourceModel<I>, I> Optional<DatasetModel> findDataset(
            BaseRepository<T, I> repository, I id, String project) {
        return repository.findByIdAndProject(id, project)
                .map(model -> datasetRepository.findByIdAndProject(model.getDataset(), model.getProject()).get());
    }
}
