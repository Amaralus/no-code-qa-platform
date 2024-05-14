package apps.amaralus.qa.platform.testcase.model;

import apps.amaralus.qa.platform.runtime.execution.properties.StepExecutionProperties;
import lombok.Data;

@Data
public class TestStep {
    private Long ordinalNumber;
    private String name;
    private String description;
    private String expectedResult;
    private StepExecutionProperties stepExecutionProperties;
}
