package apps.amaralus.qa.platform.testcase.model;

import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.label.linked.LabelLinkedModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import apps.amaralus.qa.platform.runtime.execution.ExecutionProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@KeySpace("testCase")
@Data
public class TestCaseModel implements ProjectLinkedModel<Long>, DatasetSource, LabelLinkedModel {
    @Id
    @GeneratedSequence("test-case-id")
    private Long id;
    private String name;
    private String description;
    private TestCaseStatus status;
    private Set<Long> labels = new HashSet<>();
    private List<TestStep> testSteps = new ArrayList<>();
    private ExecutionProperties executionProperties;

    private long folder;
    private long dataset;
    private String project;

    @Override
    public void addLabel(Long label) {
        labels.add(label);
    }

    @Override
    public void removeLabel(Long label) {
        labels.remove(label);
    }
}
