package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.context.TestState;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@KeySpace("test-report")
public class TestReportModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("test-report-id")
    private Long id;
    private String name;
    private String project;
    private TestState state;
    private Long testPlanId;
    private LocalTime executionTime;
    private ZonedDateTime startTime;
    private List<TestSubReport> subReports = new ArrayList<>();

    public record TestSubReport(
            TestInfo testInfo,
            TestState state,
            LocalTime executionTime,
            List<TestSubReport> subReports) {
    }
}
