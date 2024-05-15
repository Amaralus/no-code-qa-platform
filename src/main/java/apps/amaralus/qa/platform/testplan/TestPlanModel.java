package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import apps.amaralus.qa.platform.runtime.execution.properties.TestPlanExecutionProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("testPlan")
@Data
public class TestPlanModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("test-plan-id")
    private Long id;
    private String name;
    private String project;
    private long environment;
    private TestPlanExecutionProperties executionProperties;
}
