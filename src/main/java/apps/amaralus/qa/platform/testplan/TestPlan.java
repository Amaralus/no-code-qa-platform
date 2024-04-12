package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.runtime.execution.ExecutionProperties;
import lombok.Data;

import static apps.amaralus.qa.platform.testplan.AutomationDegree.AUTO;

@Data
public class TestPlan implements IdSource<Long> {
    private Long id = 0L;
    private String name;
    private long environment;
    private AutomationDegree automationDegree = AUTO;
    private ExecutionProperties executionProperties;
}
