package apps.amaralus.qa.platform.runtime.result;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TimeoutResult implements ExecutionResult {

    private final String message;

    public TimeoutResult(long timeout, TimeUnit timeUnit) {
        message = "timed out after " + timeout + " " + timeUnit.name().toLowerCase();
    }

    @Override
    public Optional<Object> payload() {
        return Optional.empty();
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
