package apps.amaralus.qa.platform.environment.itservice;

import apps.amaralus.qa.platform.environment.itservice.model.ITService;
import apps.amaralus.qa.platform.environment.itservice.model.ITServiceModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import org.springframework.stereotype.Service;

@Service
public class ITServiceService extends ProjectLinkedService<ITService, ITServiceModel, Long> {

}
