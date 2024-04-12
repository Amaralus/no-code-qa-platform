package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.runtime.execution.context.TestContext;

@FunctionalInterface
public interface StepAction {

    void execute(TestContext testContext);
}
