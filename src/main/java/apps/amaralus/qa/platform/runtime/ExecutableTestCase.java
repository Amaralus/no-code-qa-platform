package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.ExecutionGraph;
import apps.amaralus.qa.platform.runtime.execution.StageTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static apps.amaralus.qa.platform.runtime.TestState.*;

@Slf4j
@RequiredArgsConstructor
public class ExecutableTestCase implements StageTask {

    @Getter
    private final TestCaseInfo testCaseInfo;
    private final AtomicBoolean canceled = new AtomicBoolean();
    private final AtomicBoolean failed = new AtomicBoolean();
    @Setter
    private ExecutionGraph stepsExecutionGraph;
    @Getter
    private List<ExecutableTestStep> testSteps;
    @Setter
    private Runnable taskFinishCallback;
    @Setter
    @Getter
    private TestState state = CREATED;

    @Override
    public void execute() {
        if (isCanceled())
            return;

        setState(RUNNING);
        stepsExecutionGraph.execute();
    }

    public void stepsGraphFinishedCallback() {
        if (!isCanceled() && !isFailed())
            setState(COMPLETED);

        log.debug("Test {}#\"{}\" finished as {}", testCaseInfo.id(), testCaseInfo.name(), state);
        if (taskFinishCallback != null)
            taskFinishCallback.run();
    }

    public void testStepFailCallback() {
        failed.set(true);
        setState(FAILED);
        stepsExecutionGraph.cancel();
        stepsGraphFinishedCallback();
    }

    @Override
    public void cancel() {
        if (isFailed())
            return;
        canceled.set(true);
        setState(CANCELED);
        stepsExecutionGraph.cancel();
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }

    public boolean isFailed() {
        return failed.get();
    }

    public void setTestSteps(List<ExecutableTestStep> testSteps) {
        this.testSteps = testSteps;
        testSteps.forEach(testStep -> testStep.setTaskFailCallback(this::testStepFailCallback));
    }
}
