package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.common.BaseRepository;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;

import java.util.Optional;

public interface DatasetRepository extends BaseRepository<DatasetModel, Long> {

    Optional<DatasetModel> findByPathAndProject(String path, String project);
}
