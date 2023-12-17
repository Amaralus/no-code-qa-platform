package apps.amaralus.qa.platform.dataset.repository;

import apps.amaralus.qa.platform.dataset.model.AliasModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface AliasRepository extends KeyValueRepository<AliasModel, Long> {

    List<AliasModel> findAllByKey_Project(String project);

    Optional<AliasModel> findByNameAndKey_Project(String name, String project);

    List<AliasModel> findAllByNameAndKey_Project(String name, String project);
}
