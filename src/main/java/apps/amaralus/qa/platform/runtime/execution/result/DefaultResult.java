package apps.amaralus.qa.platform.runtime.execution.result;

public record DefaultResult(String message, boolean failed, boolean canceled) implements ExecutionResult {

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
