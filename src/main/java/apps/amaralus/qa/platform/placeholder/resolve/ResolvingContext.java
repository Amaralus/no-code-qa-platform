package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.PlaceholderType;

import java.util.Optional;

public interface ResolvingContext {

    Optional<DatasetModel> getDatasetById(PlaceholderType placeholderType, Long id, String project);
}
