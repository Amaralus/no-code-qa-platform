package apps.amaralus.qa.platform.runtime.result;

public record DefaultResult(String message, boolean failed) implements ExecutionResult {

    public DefaultResult(boolean failed) {
        this("", failed);
    }

    @Override
    public boolean isFailed() {
        return failed;
    }
}
