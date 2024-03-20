package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.label.model.api.Label;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService extends ProjectLinkedService<Label, LabelModel, Long> {
}
