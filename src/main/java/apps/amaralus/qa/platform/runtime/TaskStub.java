package apps.amaralus.qa.platform.runtime;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public final class TaskStub implements StageTask {
    private final AtomicBoolean canceled = new AtomicBoolean();
    private Runnable callback;

    @Override
    public void execute() {
        if (isCanceled())
            return;

        log.debug("stub task");
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
