package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.result.ErrorResult;
import apps.amaralus.qa.platform.runtime.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.result.TimeoutResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static apps.amaralus.qa.platform.runtime.TestState.*;

@Slf4j
@RequiredArgsConstructor
public class ExecutableTestStep implements StageTask, ExecutorServiceAware {

    private final String testStepName;
    private final StepAction stepAction;
    // временно тут
    private final TestContext testContext = new TestContext();
    private final AtomicBoolean canceled = new AtomicBoolean();
    @Setter
    private ExecutorService executorService;
    private long timeout = 10L;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    @Setter
    private Runnable taskFinishCallback;
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
        stepTask = CompletableFuture.supplyAsync(this::executeAction, executorService);
        var handleTask = stepTask.completeOnTimeout(timeoutResult(), timeout, timeUnit)
                .exceptionally(ErrorResult::new)
                .thenAccept(this::handleResult);

        if (taskFinishCallback != null)
            handleTask.thenRun(this::executeCallback);
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
        log.debug("Step \"{}\" finished as {}: {}", testStepName, state, resultMessage);
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
