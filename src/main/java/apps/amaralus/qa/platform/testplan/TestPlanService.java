package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import org.springframework.stereotype.Service;

@Service
public class TestPlanService extends ProjectLinkedService<TestPlan, TestPlanModel, Long> {
}
