package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.action.ActionType;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class StepExecutionProperties {
    private long executionAction;
    private ActionType actionType = ActionType.NONE;
    private long timeout = 10L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public StepExecutionProperties(long executionAction) {
        this.executionAction = executionAction;
    }

    public StepExecutionProperties(long executionAction, ActionType actionType) {
        this.executionAction = executionAction;
        this.actionType = actionType;
    }

    public StepExecutionProperties(long executionAction, long timeout, TimeUnit timeUnit) {
        this.executionAction = executionAction;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public StepExecutionProperties(long executionAction, ActionType actionType, long timeout, TimeUnit timeUnit) {
        this.executionAction = executionAction;
        this.actionType = actionType;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }
}
