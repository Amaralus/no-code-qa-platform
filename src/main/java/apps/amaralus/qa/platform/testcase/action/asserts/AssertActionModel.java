package apps.amaralus.qa.platform.testcase.action.asserts;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@NoArgsConstructor
@KeySpace("assert-action")
public class AssertActionModel implements ProjectLinkedModel<Long> {

    public AssertActionModel(Long id, String project, Assertion assertion, Object expected, Object actual) {
        this.id = id;
        this.project = project;
        this.assertion = assertion;
        this.expected = validateAndConvert(expected);
        this.actual = validateAndConvert(actual);
    }

    @Id
    @GeneratedSequence("assert-action-id")
    private Long id;
    private String project;
    private Assertion assertion;
    private Object expected;
    private Object actual;

    public void setExpected(Object expected) {
        this.expected = validateAndConvert(expected);
    }

    public void setActual(Object actual) {
        this.actual = validateAndConvert(actual);
    }

    private Object validateAndConvert(Object value) {
        if (value == null ||
            value instanceof Number ||
            value instanceof Boolean ||
            value instanceof String)
            return value;
        else
            throw new IllegalArgumentException();
    }
}
