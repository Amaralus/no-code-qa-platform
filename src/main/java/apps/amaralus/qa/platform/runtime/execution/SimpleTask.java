package apps.amaralus.qa.platform.runtime.execution;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public final class SimpleTask implements StageTask {
    private final AtomicBoolean canceled = new AtomicBoolean();
    private final Runnable action;
    private Runnable callback;

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
        callback.run();
    }

    @Override
    public void cancel() {
        canceled.set(true);
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }

    @Override
    public void setTaskFinishCallback(Runnable taskFinishCallback) {
        callback = taskFinishCallback;
    }
}
