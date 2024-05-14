package apps.amaralus.qa.platform.runtime.execution.properties;

import apps.amaralus.qa.platform.runtime.action.ActionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static apps.amaralus.qa.platform.runtime.action.ActionType.REST;

@Data
@NoArgsConstructor
public class StepExecutionProperties implements TaskExecutionProperties {

    private long executionAction;
    private ActionType actionType = ActionType.NONE;
    private long timeout = 10L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private Set<Long> executeAfterTasks = new HashSet<>();
    private Set<Long> dependsFromTasks = new HashSet<>();

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

    @Override
    public void addExecuteAfter(@NotNull Long taskId) {
        Assert.notNull(taskId, TASK_ID_IS_NULL_MESSAGE);
        executeAfterTasks.add(taskId);
    }

    @Override
    public void removeExecuteAfter(@NotNull Long taskId) {
        Assert.notNull(taskId, TASK_ID_IS_NULL_MESSAGE);
        executeAfterTasks.remove(taskId);
    }

    @Override
    public void addDependsFrom(@NotNull Long taskId) {
        Assert.notNull(taskId, TASK_ID_IS_NULL_MESSAGE);
        dependsFromTasks.add(taskId);
    }

    @Override
    public void removeDependsFrom(@NotNull Long taskId) {
        Assert.notNull(taskId, TASK_ID_IS_NULL_MESSAGE);
        dependsFromTasks.remove(taskId);
    }

    @Override
    public boolean initialStepRequired() {
        return actionType == REST;
    }
}
