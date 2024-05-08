package apps.amaralus.qa.platform.runtime.execution;

import apps.amaralus.qa.platform.runtime.action.StepAction;
import apps.amaralus.qa.platform.runtime.execution.context.TestContext;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.execution.result.ErrorResult;
import apps.amaralus.qa.platform.runtime.execution.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.execution.result.TestFailedException;
import apps.amaralus.qa.platform.runtime.execution.result.TimeoutResult;
import apps.amaralus.qa.platform.testplan.report.TestReport;
import com.google.common.base.Throwables;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static apps.amaralus.qa.platform.runtime.execution.context.TestState.*;

@Slf4j
public class ExecutableTestStep extends ExecutableTestSupport implements StageTask, RuntimeExecutorAware {

    private final StepAction stepAction;
    @Setter
    private TestContext testContext;
    @Setter
    private RuntimeExecutor runtimeExecutor;
    private long timeout;
    private TimeUnit timeUnit;
    @Setter
    private Runnable taskFinishCallback;
    @Setter
    private Runnable taskFailCallback;
    private CompletableFuture<ExecutionResult> stepTask;

    public ExecutableTestStep(TestInfo testInfo, StepAction stepAction) {
        super(testInfo);
        this.stepAction = stepAction;
    }


    @Override
    public void execute() {
        if (isCanceled())
            return;

        setState(RUNNING);
        timer.start();

        stepTask = runtimeExecutor.supplyAsync(this::executeAction);
        var handleTask = stepTask.completeOnTimeout(timeoutResult(), timeout, timeUnit)
                .exceptionally(this::handleException)
                .thenAccept(this::handleResult);

        runtimeExecutor.runAsync(handleTask, this::executeCallback);
    }

    @Override
    public void cancel() {
        super.cancel();
        if (stepTask != null) {
            // значение true никак не влияет на прерывание потока, всегда работает как false
            stepTask.cancel(false);
            if (stepAction instanceof Cancelable cancelableAction)
                cancelableAction.cancel();
        } else {
            timer.stop();
            setState(CANCELED);
        }
    }

    private ExecutionResult executeAction() {
        stepAction.execute(testContext);
        return testContext.getExecutionResult();
    }

    private void executeCallback() {
        if (isCanceled())
            return;

        if (state == FAILED || state == ERROR)
            taskFailCallback.run();
        else
            taskFinishCallback.run();
    }

    private void handleResult(ExecutionResult result) {
        if (result.isError())
            onError((ErrorResult) result);
        else if (result.isTimeout())
            setState(FAILED);
        else if (result.isFailed())
            setState(FAILED);
        else if (result.isCanceled())
            setState(CANCELED);
        else
            setState(COMPLETED);

        timer.stop();
        resultMessage = result.message();
        log.debug("Step \"{}\" finished as {}: {}", testInfo.name(), state, resultMessage);
    }

    private ExecutionResult handleException(Throwable throwable) {
        var rootCause = Throwables.getRootCause(throwable);

        if (rootCause instanceof CancellationException)
            return ExecutionResult.cancel();
        if (rootCause instanceof TestFailedException)
            return ExecutionResult.fail(rootCause.getMessage());

        return new ErrorResult(throwable);
    }

    private void onError(ErrorResult errorResult) {
        setState(ERROR);
        log.error("Test-step error", errorResult.throwable());
    }

    private TimeoutResult timeoutResult() {
        return new TimeoutResult(timeout, timeUnit);
    }

    public void timeout(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public TestReport getReport() {
        var report = super.getReport();
        report.setDeep(2);
        return report;
    }
}
