package apps.amaralus.qa.platform.runtime.result;

public interface ExecutionResult {

    default boolean isFailed() {
        return false;
    }

    default boolean isTimeout() {
        return false;
    }

    default boolean isError() {
        return false;
    }

    default String message() {
        return "";
    }
}
