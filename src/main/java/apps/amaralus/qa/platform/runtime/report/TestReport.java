package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.context.TestState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@RequiredArgsConstructor
public class TestReport implements IdSource<Long> {
    @Setter
    private Long id = 0L;
    private final TestInfo testInfo;
    private final TestState state;
    private final String message;
    private final LocalTime executionTime;
    @Setter
    private List<TestReport> subReports = new ArrayList<>();
    @Setter
    private int deep;

    @Override
    public String toString() {
        return String.format("%s%s finished as %s, message: \"%s\", time: %s%s",
                deepShift(), testInfo, state, message, executionTime, subReports.stream()
                        .map(testReport -> "\n" + testReport)
                        .collect(Collectors.joining()));
    }

    private String deepShift() {
        return deep <= 0 ? "" : " ".repeat(4).repeat(deep);
    }
}
