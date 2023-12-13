package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.Cancelable;
import apps.amaralus.qa.platform.runtime.execution.Executable;
import apps.amaralus.qa.platform.runtime.execution.ExecutionGraph;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class ExecutableTestPlan implements Executable, Cancelable {

    private final ExecutionGraph executionGraph;
    @Getter
    private final List<ExecutableTestCase> testCases;
    private final AtomicBoolean canceled = new AtomicBoolean();

    @Override
    public void execute() {
        if (isCanceled())
            return;
        executionGraph.execute();
    }

    @Override
    public void cancel() {
        canceled.set(true);
        executionGraph.cancel();
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }
}
