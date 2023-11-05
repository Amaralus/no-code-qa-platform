package apps.amaralus.qa.platform.runtime.result;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SuccessfulResult implements ExecutionResult{

    private final Object payload;

    public SuccessfulResult() {
        this(null);
    }

    @Override
    public Optional<Object> payload() {
        return Optional.ofNullable(payload);
    }
}
