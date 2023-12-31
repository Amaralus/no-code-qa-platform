package apps.amaralus.qa.platform.dataset.repository;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetRepository extends KeyValueRepository<DatasetModel, Long> {
    List<DatasetModel> findAllByProject(String project);
    Optional<DatasetModel> findByPathAndProject(String path, String project);
}
