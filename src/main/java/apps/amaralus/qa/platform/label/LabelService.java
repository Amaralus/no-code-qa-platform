package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.label.model.api.Label;
import apps.amaralus.qa.platform.project.linked.AbstractProjectLinkedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService extends AbstractProjectLinkedService<Label, LabelModel, Long> {
}
