package apps.amaralus.qa.platform.runtime;

@FunctionalInterface
public interface StepAction {

    void execute(TestContext testContext);
}
