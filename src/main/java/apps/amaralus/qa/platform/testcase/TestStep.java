package apps.amaralus.qa.platform.testcase;

import lombok.Data;

@Data
public class TestStep {
    private String name;
    private String description;
    private String expectedResult;
}
