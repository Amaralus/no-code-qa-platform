package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AliasService extends ProjectLinkedService<Alias, AliasModel, Long> {
}
