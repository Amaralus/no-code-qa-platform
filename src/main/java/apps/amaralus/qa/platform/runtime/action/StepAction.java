package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.runtime.TestContext;

@FunctionalInterface
public interface StepAction {

    void execute(TestContext testContext);
}
