package apps.amaralus.qa.platform.dataset;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface DatasetRepository extends KeyValueRepository<DatasetModel, Long> {
    void deleteAllByProject(String project);
}
