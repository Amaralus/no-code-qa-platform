package apps.amaralus.qa.platform.runtime.resolve;

import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.placeholder.resolve.ResolvingContext;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Setter(AccessLevel.PACKAGE)
public class RuntimeResolvingContext implements ResolvingContext {

    private DatasetModel projectDataset;
    private DatasetModel testCaseDataset;
    private Map<Long, Optional<DatasetModel>> foldersDatasets;
    private Map<Long, Optional<DatasetModel>> allDatasets;
    private Map<String, Optional<AliasModel>> aliases;

    @Override
    public Optional<DatasetModel> findDataset(DefaultPlaceholderType placeholderType, Long id) {

        return switch (placeholderType) {
            case DATASET -> findDataset(id);
            case PROJECT -> Optional.of(projectDataset);
            case FOLDER -> foldersDatasets.getOrDefault(id, Optional.empty());
            case TESTCASE -> Optional.of(testCaseDataset);
            // todo доделать после доработки окружения
            case ENVIRONMENT -> Optional.empty();
            case SERVICE -> Optional.empty();
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<DatasetModel> findDataset(Long id) {
        return allDatasets.getOrDefault(id, Optional.empty());
    }

    @Override
    public Optional<AliasModel> findAlias(String name) {
        return aliases.getOrDefault(name, Optional.empty());
    }

    @Override
    public void setProjectContext(ProjectContext projectContext) {
        // do nothing
    }

    @Override
    public boolean isGlobalContext() {
        return false;
    }
}
