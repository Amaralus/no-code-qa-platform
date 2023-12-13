package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import apps.amaralus.qa.platform.runtime.ExecutionProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("testPlan")
@Data
public class TestPlanModel {
    @Id
    @GeneratedSequence("test-plan-id")
    private long id;
    private String name;
    private String project;
    private ExecutionProperties executionProperties;
}
