package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.project.context.ProjectContext;

import java.util.Optional;

public interface ResolvingContext {

    Optional<DatasetModel> findDataset(DefaultPlaceholderType placeholderType, Long id);

    Optional<DatasetModel> findDataset(Long id);

    Optional<AliasModel> findAlias(String name);

    void setProjectContext(ProjectContext projectContext);
}
