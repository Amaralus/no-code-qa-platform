package apps.amaralus.qa.platform.runtime;

public interface StageTask {

    void execute();

    void setTaskFinishCallback(Runnable taskFinishCallback);
}
