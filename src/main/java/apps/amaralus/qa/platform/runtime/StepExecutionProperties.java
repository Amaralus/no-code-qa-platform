package apps.amaralus.qa.platform.runtime;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class StepExecutionProperties {
    private long executionAction;
    private long timeout = 10L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public StepExecutionProperties(long executionAction) {
        this.executionAction = executionAction;
    }

    public StepExecutionProperties(long executionAction, long timeout, TimeUnit timeUnit) {
        this.executionAction = executionAction;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }
}
