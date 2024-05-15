package apps.amaralus.qa.platform.runtime.execution;

import apps.amaralus.qa.platform.runtime.execution.properties.TaskExecutionProperties;

import java.util.function.Predicate;

public interface StageTask extends Executable, Cancelable {

    Predicate<StageTask> DEFAULT_CONDITION = stageTask -> true;

    void setTaskFinishCallback(Runnable taskFinishCallback);

    void setExecutionCondition(Predicate<StageTask> executionCondition);

    Predicate<StageTask> getExecutionCondition();

    default boolean checkExecutionCondition() {
        return getExecutionCondition().test(this);
    }

    TaskExecutionProperties getExecutionProperties();

    Long getTaskId();
}
