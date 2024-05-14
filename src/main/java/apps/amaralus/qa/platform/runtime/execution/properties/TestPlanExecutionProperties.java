package apps.amaralus.qa.platform.runtime.execution.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanExecutionProperties implements GraphExecutionProperties {
    private boolean parallelExecution;
    private boolean failFast;
}
