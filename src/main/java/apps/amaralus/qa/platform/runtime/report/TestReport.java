package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.runtime.TestInfo;
import apps.amaralus.qa.platform.runtime.TestState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class TestReport {
    private final TestInfo testInfo;
    private final TestState state;
    private final String message;
    private final LocalTime executionTime;
    @Setter
    @Getter
    private List<TestReport> subReports = new ArrayList<>();
    @Setter
    private int deep;

    @Override
    public String toString() {
        return String.format("%s#%d \"%s\" finished as %s, message: \"%s\", time: %s%s",
                deepShift(), testInfo.id(), testInfo.name(), state, message, executionTime, subReports.stream()
                        .map(testReport -> "\n" + testReport)
                        .collect(Collectors.joining()));
    }

    private String deepShift() {
        return deep <= 0 ? "" : " ".repeat(4).repeat(deep);
    }
}
