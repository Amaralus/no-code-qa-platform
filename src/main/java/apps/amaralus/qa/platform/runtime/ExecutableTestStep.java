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

import static apps.amaralus.qa.platform.runtime.TestState.*;

@Slf4j
@RequiredArgsConstructor
public class ExecutableTestStep implements StageTask {

    private final StepAction stepAction;
    private final long timeout;
    private final TimeUnit timeUnit;
    private final ExecutorService executorService;
    @Setter
    private Runnable taskFinishCallback;
    @Getter
    @Setter
    private TestState state = CREATED;
    @Getter
    private String resultMessage = "";
    private CompletableFuture<ExecutionResult> stepTask;

    public void execute() {
        setState(RUNNING);
        stepTask = CompletableFuture.supplyAsync(stepAction, executorService);
        var handleTask = stepTask.completeOnTimeout(new TimeoutResult(timeout, timeUnit), timeout, timeUnit)
                .exceptionally(ErrorResult::new)
                .thenAccept(this::handleResult);

        if (taskFinishCallback != null)
            handleTask.thenRun(taskFinishCallback);
    }

    public void cancel() {
        stepTask.cancel(false);
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
        log.debug("Step finished as {}: {}", state, resultMessage);
    }

    private void onError(ErrorResult errorResult) {
        if (CancellationException.class == errorResult.throwable().getClass()) {
            setState(CANCELED);
            return;
        }
        setState(ERROR);
        log.error("Test-step error", errorResult.throwable());
    }
}