package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.execution.RuntimeExecutor;
import apps.amaralus.qa.platform.runtime.execution.RuntimeExecutorAware;
import apps.amaralus.qa.platform.runtime.execution.StageTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractExecutionScheduler implements ExecutionScheduler {

    protected RuntimeExecutor runtimeExecutor;

    protected void injectExecutorService(List<? extends StageTask> tasks) {
        for (var task : tasks) {
            if (task instanceof RuntimeExecutorAware aware)
                aware.setRuntimeExecutor(runtimeExecutor);
        }
    }

    @Override
    @Autowired
    public void setRuntimeExecutor(RuntimeExecutor runtimeExecutor) {
        this.runtimeExecutor = runtimeExecutor;
    }
}
