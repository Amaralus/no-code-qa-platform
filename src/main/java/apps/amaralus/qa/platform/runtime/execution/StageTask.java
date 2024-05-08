package apps.amaralus.qa.platform.runtime.execution;

import java.util.function.Predicate;

public interface StageTask extends Executable, Cancelable {

    void setTaskFinishCallback(Runnable taskFinishCallback);

    void setExecutionCondition(Predicate<StageTask> executionCondition);

    Predicate<StageTask> getExecutionCondition();

    default boolean checkExecutionCondition() {
        return getExecutionCondition().test(this);
    }
}
