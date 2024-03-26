package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.runtime.ExecutionProperties;
import lombok.Data;

@Data
public class TestPlan implements IdSource<Long> {
    private Long id = 0L;
    private String name;
    private long environment;
    private ExecutionProperties executionProperties;
}
