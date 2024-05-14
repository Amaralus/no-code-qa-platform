package apps.amaralus.qa.platform.runtime.execution.properties;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface TaskExecutionProperties {

    String TASK_ID_IS_NULL_MESSAGE = "taskId must not be null!";

    @NotNull Set<Long> getExecuteAfterTasks();

    @NotNull Set<Long> getDependsFromTasks();

    void addExecuteAfter(@NotNull Long taskId);

    void removeExecuteAfter(@NotNull Long taskId);

    void addDependsFrom(@NotNull Long taskId);

    void removeDependsFrom(@NotNull Long taskId);

    default boolean initialStepRequired() {
        return false;
    }
}
