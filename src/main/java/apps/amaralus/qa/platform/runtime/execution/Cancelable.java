package apps.amaralus.qa.platform.runtime.execution;

public interface Cancelable {

    void cancel();

    boolean isCanceled();
}
