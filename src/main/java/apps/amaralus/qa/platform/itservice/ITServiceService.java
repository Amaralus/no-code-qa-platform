package apps.amaralus.qa.platform.itservice;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.itservice.model.ITService;
import apps.amaralus.qa.platform.itservice.model.ITServiceModel;
import org.springframework.stereotype.Service;

@Service
public class ITServiceService extends DatasetLinkedService<ITService, ITServiceModel, Long> {

}
