package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedRepository;

import java.util.Optional;

public interface AliasRepository extends ProjectLinkedRepository<AliasModel, Long> {

    Optional<AliasModel> findByNameAndProject(String name, String project);
}
