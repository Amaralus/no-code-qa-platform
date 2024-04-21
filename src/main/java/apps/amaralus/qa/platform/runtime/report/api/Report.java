package apps.amaralus.qa.platform.runtime.report.api;

import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.context.TestState;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Report {

    private Long id;
    private String name;
    private String project;
    private TestState state;
    private Long testPlanId;
    private TestInfo testInfo;
    private LocalTime executionTime;
    private List<SubReport> subReports = new ArrayList<>();

    public record SubReport(
            TestInfo testInfo,
            TestState state,
            LocalTime executionTime,
            List<SubReport> subReports) {
    }
}
