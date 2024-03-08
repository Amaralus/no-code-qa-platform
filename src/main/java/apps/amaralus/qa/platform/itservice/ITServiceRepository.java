package apps.amaralus.qa.platform.itservice;

import apps.amaralus.qa.platform.itservice.model.ITServiceModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface ITServiceRepository extends KeyValueRepository<ITServiceModel, Long> {
    List<ITServiceModel> findAllByProject(String project);
}
