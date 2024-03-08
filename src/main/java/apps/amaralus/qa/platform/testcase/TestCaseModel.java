package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.common.model.DatasetSourceModel;
import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import apps.amaralus.qa.platform.runtime.ExecutionProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.ArrayList;
import java.util.List;

@KeySpace("testCase")
@Data
public class TestCaseModel implements DatasetSourceModel<Long> {
    @Id
    @GeneratedSequence("test-case-id")
    private Long id;
    private String name;
    private String description;
    private Status status;
    private List<LabelModel> labels;
    private List<TestStep> testSteps = new ArrayList<>();
    private ExecutionProperties executionProperties;

    private long folder;
    private long dataset;
    private String project;
}
