package apps.amaralus.qa.platform.dataset.repository;

import apps.amaralus.qa.platform.dataset.model.AliasModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface AliasRepository extends KeyValueRepository<AliasModel, Long> {

    List<AliasModel> findAllByProject(String project);

    Optional<AliasModel> findByNameAndProject(String name, String project);

    List<AliasModel> findAllByNameAndProject(String name, String project);
}
