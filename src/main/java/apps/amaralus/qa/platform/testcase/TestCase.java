package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.List;

@KeySpace("testCase")
@Data
public class TestCase {
    @Id
    @GeneratedSequence("test-case-id")
    private long id;
    private String name;
    private String description;
    private Status status;
    private List<Label> labels;
    private List<TestStep> testSteps;
    private long folder;
    private String project;
}
