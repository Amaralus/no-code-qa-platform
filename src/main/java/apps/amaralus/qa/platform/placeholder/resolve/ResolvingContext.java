package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.PlaceholderType;
import apps.amaralus.qa.platform.project.context.ProjectContext;

import java.util.Optional;

public interface ResolvingContext {

    Optional<DatasetModel> findDataset(PlaceholderType placeholderType, Long id);

    void setProjectContext(ProjectContext projectContext);
}
