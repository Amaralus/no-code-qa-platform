package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.testcase.TestCaseModel;

public record TestCaseInfo(long id, String name) {

    public TestCaseInfo(TestCaseModel testCaseModel) {
        this(testCaseModel.getId(), testCaseModel.getName());
    }
}
