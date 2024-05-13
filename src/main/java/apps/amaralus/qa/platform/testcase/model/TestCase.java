package apps.amaralus.qa.platform.testcase.model;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.runtime.execution.ExecutionProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestCase implements IdSource<Long>, DatasetSource {
    private Long id = 0L;
    private String name;
    private String description;
    private TestCaseStatus status;
    private List<Long> labels = new ArrayList<>();
    private List<TestStep> testSteps = new ArrayList<>();
    private ExecutionProperties executionProperties;

    private Long folder;
    private long dataset;
}
