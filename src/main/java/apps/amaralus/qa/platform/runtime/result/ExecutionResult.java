package apps.amaralus.qa.platform.runtime.result;

import java.util.Optional;

public interface ExecutionResult {

    Optional<Object> payload();

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
