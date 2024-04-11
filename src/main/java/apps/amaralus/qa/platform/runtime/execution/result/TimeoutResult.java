package apps.amaralus.qa.platform.runtime.execution.result;

import java.util.concurrent.TimeUnit;

public class TimeoutResult implements ExecutionResult {

    private final String message;

    public TimeoutResult(long timeout, TimeUnit timeUnit) {
        message = "timed out after " + timeout + " " + timeUnit.name().toLowerCase();
    }

    @Override
    public boolean isTimeout() {
        return true;
    }

    @Override
    public String message() {
        return message;
    }
}
