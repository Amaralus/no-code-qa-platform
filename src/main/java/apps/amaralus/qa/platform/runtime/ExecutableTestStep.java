package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.RuntimeExecutor;
import apps.amaralus.qa.platform.runtime.execution.RuntimeExecutorAware;
import apps.amaralus.qa.platform.runtime.execution.StageTask;
import apps.amaralus.qa.platform.runtime.execution.StepAction;
import apps.amaralus.qa.platform.runtime.result.ErrorResult;
import apps.amaralus.qa.platform.runtime.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.result.TimeoutResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static apps.amaralus.qa.platform.runtime.TestState.*;

@Slf4j
@RequiredArgsConstructor
public class ExecutableTestStep implements StageTask, RuntimeExecutorAware {

    @Getter
    private final TestStepInfo testStepInfo;
    private final StepAction stepAction;
    // временно тут
    private final TestContext testContext = new TestContext();
    private final AtomicBoolean canceled = new AtomicBoolean();
    @Setter
    private RuntimeExecutor runtimeExecutor;
    private long timeout;
    private TimeUnit timeUnit;
    @Setter
    private Runnable taskFinishCallback;
    @Setter
    private Runnable taskFailCallback;
    @Getter
    @Setter
    private TestState state = CREATED;
    @Getter
    private String resultMessage = "";
    private CompletableFuture<ExecutionResult> stepTask;

    @Override
    public void execute() {
        if (isCanceled())
            return;

        setState(RUNNING);
        stepTask = runtimeExecutor.supplyAsync(this::executeAction);
        var handleTask = stepTask.completeOnTimeout(timeoutResult(), timeout, timeUnit)
                .exceptionally(ErrorResult::new)
                .thenAccept(this::handleResult);

        runtimeExecutor.runAsync(handleTask, this::executeCallback);
    }

    @Override
    public void cancel() {
        canceled.set(true);
        if (stepTask != null)
            // значение true никак не влияет на прерывание потока, всегда работает как false
            stepTask.cancel(false);
        else
            state = CANCELED;
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
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
        else
            setState(COMPLETED);

        if (state != CANCELED)
            resultMessage = result.message();
        log.debug("Step \"{}\" finished as {}: {}", testStepInfo.name(), state, resultMessage);
    }

    private void onError(ErrorResult errorResult) {
        if (CancellationException.class == errorResult.throwable().getClass()) {
            setState(CANCELED);
            return;
        }
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
}
