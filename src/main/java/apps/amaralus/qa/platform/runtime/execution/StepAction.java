package apps.amaralus.qa.platform.runtime.execution;

import apps.amaralus.qa.platform.runtime.TestContext;

@FunctionalInterface
public interface StepAction {

    void execute(TestContext testContext);
}
