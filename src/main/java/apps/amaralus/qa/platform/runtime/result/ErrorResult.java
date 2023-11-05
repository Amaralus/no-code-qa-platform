package apps.amaralus.qa.platform.runtime.result;

import com.google.common.base.Throwables;

import java.util.Optional;

public record ErrorResult(Throwable throwable) implements ExecutionResult {

    @Override
    public Optional<Object> payload() {
        return Optional.empty();
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public String message() {
        return throwable.getClass().getName() + ": " + Throwables.getRootCause(throwable).getMessage();
    }
}
