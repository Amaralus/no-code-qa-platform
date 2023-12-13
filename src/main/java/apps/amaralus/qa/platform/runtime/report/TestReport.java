package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.runtime.TestState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
public class TestReport {
    protected final String name;
    protected final TestState state;
    protected final String message;
    protected final LocalTime executionTime;

    @Override
    public String toString() {
        return String.format("Step \"%s\" finished as %s, message: \"%s\", time: %s", name, state, message, executionTime);
    }
}
