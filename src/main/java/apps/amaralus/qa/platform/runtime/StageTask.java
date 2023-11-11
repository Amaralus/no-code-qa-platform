package apps.amaralus.qa.platform.runtime;

public interface StageTask extends Executable {

    void setTaskFinishCallback(Runnable taskFinishCallback);
}
