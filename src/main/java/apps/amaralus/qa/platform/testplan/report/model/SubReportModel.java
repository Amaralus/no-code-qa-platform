package apps.amaralus.qa.platform.testplan.report.model;

import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.context.TestState;

import java.time.LocalTime;
import java.util.List;

public record SubReportModel(TestInfo testInfo,
                             TestState state,
                             LocalTime executionTime,
                             List<SubReportModel> subReports) {
}
