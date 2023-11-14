package apps.amaralus.qa.platform.runtime;

public interface StageTask extends Executable, Cancelable {

    void setTaskFinishCallback(Runnable taskFinishCallback);
}
