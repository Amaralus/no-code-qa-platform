package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.project.linked.AbstractProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetService extends AbstractProjectLinkedService<Dataset, DatasetModel, Long> {
}
