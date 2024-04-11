package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.label.model.api.Label;
import apps.amaralus.qa.platform.runtime.execution.ExecutionProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestCase implements IdSource<Long>, DatasetSource {
    private Long id = 0L;
    private String name;
    private String description;
    private Status status;
    private List<Label> labels;
    private List<TestStep> testSteps = new ArrayList<>();
    private ExecutionProperties executionProperties;

    private long folder;
    private long dataset;
}
