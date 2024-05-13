package apps.amaralus.qa.platform.testcase.model;

import apps.amaralus.qa.platform.runtime.execution.properties.GraphExecutionProperties;
import apps.amaralus.qa.platform.runtime.execution.properties.TaskExecutionProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseExecutionProperties implements GraphExecutionProperties, TaskExecutionProperties {

    private static final String TASK_ID_IS_NULL_MESSAGE = "taskId must not be null!";

    private boolean parallelExecution;
    private boolean failFast;
    private Set<Long> executeAfterTasks = new HashSet<>();
    private Set<Long> dependsFromTasks = new HashSet<>();

    public TestCaseExecutionProperties(boolean parallelExecution, boolean failFast) {
        this.parallelExecution = parallelExecution;
        this.failFast = failFast;
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
}
