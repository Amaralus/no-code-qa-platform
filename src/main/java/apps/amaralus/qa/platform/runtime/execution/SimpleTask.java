package apps.amaralus.qa.platform.runtime.execution;

import apps.amaralus.qa.platform.runtime.execution.properties.TaskExecutionProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Slf4j
public final class SimpleTask implements StageTask {
    private final AtomicBoolean canceled = new AtomicBoolean();
    private final Runnable action;
    @Setter
    private Runnable taskFinishCallback;
    @Setter
    @Getter
    private Predicate<StageTask> executionCondition = DEFAULT_CONDITION;
    @Setter
    @Getter
    private TaskExecutionProperties executionProperties;

    public SimpleTask() {
        this(null);
    }

    public SimpleTask(@Nullable Runnable action) {
        this.action = action;
    }

    @Override
    public void execute() {
        if (isCanceled())
            return;
        if (action != null)
            action.run();
        taskFinishCallback.run();
    }

    @Override
    public void cancel() {
        canceled.set(true);
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }
}
