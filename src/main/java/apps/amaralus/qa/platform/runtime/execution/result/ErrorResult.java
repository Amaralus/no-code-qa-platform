package apps.amaralus.qa.platform.runtime.execution.result;

import com.google.common.base.Throwables;

public record ErrorResult(Throwable throwable) implements ExecutionResult {

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public String message() {
        return throwable.getClass().getName() + ": " + Throwables.getRootCause(throwable).getMessage();
    }
}
