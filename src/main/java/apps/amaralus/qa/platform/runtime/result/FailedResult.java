package apps.amaralus.qa.platform.runtime.result;

import java.util.Optional;

public record FailedResult(String message) implements ExecutionResult {

    public Optional<Object> payload() {
        return Optional.empty();
    }

    @Override
    public boolean isFailed() {
        return true;
    }
}
