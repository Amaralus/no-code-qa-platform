package apps.amaralus.qa.platform.runtime;

public interface Cancelable {

    void cancel();

    boolean isCanceled();
}
