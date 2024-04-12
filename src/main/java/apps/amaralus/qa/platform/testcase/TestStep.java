package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.runtime.execution.StepExecutionProperties;
import lombok.Data;

@Data
public class TestStep {
    private String name;
    private String description;
    private String expectedResult;
    private StepExecutionProperties stepExecutionProperties;
}
