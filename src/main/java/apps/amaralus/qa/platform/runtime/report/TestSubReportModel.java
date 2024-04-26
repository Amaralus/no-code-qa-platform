package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.context.TestState;

import java.time.LocalTime;
import java.util.List;

public record TestSubReportModel(TestInfo testInfo,
                                 TestState state,
                                 LocalTime executionTime,
                                 List<TestSubReportModel> subReports) {
}
