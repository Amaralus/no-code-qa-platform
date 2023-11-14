package apps.amaralus.qa.platform.runtime;

public final class TaskStub implements StageTask {
    private Runnable callback;

    @Override
    public void execute() {
        callback.run();
    }

    @Override
    public void setTaskFinishCallback(Runnable taskFinishCallback) {
        callback = taskFinishCallback;
    }
}
