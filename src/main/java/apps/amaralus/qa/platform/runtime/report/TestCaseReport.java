package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.runtime.TestState;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestCaseReport extends TestReport {

    private final List<TestReport> stepReports;

    public TestCaseReport(String name, TestState state, LocalTime executionTime, List<TestReport> stepReports) {
        super(name, state, "", executionTime);
        this.stepReports = stepReports;
    }

    @Override
    public String toString() {
        return String.format("Test \"%s\" finished as %s, time: %s%s",
                name,
                state,
                executionTime, stepReports.stream()
                        .map(testReport -> "\n\t" + testReport.toString())
                        .collect(Collectors.joining())
        );
    }
}
