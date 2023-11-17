package apps.amaralus.qa.platform.runtime.execution;

public interface StageTask extends Executable, Cancelable {

    void setTaskFinishCallback(Runnable taskFinishCallback);
}
