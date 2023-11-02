package apps.amaralus.qa.platform.service;

import apps.amaralus.qa.platform.service.model.ITServiceModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface ITServiceRepository extends KeyValueRepository<ITServiceModel, Long> {

    void deleteAllByProject(String project);
}
