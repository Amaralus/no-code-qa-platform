package apps.amaralus.qa.platform.runtime.execution.result;

import static com.google.common.base.Strings.nullToEmpty;

public interface ExecutionResult {

    default boolean isFailed() {
        return false;
    }

    default boolean isTimeout() {
        return false;
    }

    default boolean isCanceled() {
        return false;
    }

    default boolean isError() {
        return false;
    }

    default String message() {
        return "";
    }

    static DefaultResult success(String message) {
        return new DefaultResult(nullToEmpty(message), false, false);
    }

    static DefaultResult success() {
        return new DefaultResult("", false, false);
    }

    static DefaultResult fail(String message) {
        return new DefaultResult(nullToEmpty(message), true, false);
    }

    static DefaultResult cancel() {
        return new DefaultResult("", false, true);
    }
}
