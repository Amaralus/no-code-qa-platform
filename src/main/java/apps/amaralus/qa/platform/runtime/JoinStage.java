package apps.amaralus.qa.platform.runtime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
public class JoinStage implements ExecutionStage {
    private final AtomicInteger counter = new AtomicInteger();
    private final int target;
    private final ExecutableTestStep nextStep;
    private final ExecutorService executorService;

    @Override
    public void execute() {
        int value = counter.incrementAndGet();
        if (value == target)
            nextStep.execute(executorService);
    }
}
