package apps.amaralus.qa.platform.runtime.execution.schedule;

import apps.amaralus.qa.platform.runtime.execution.ExecutorServiceAware;
import apps.amaralus.qa.platform.runtime.execution.StageTask;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.ExecutorService;

public abstract class AbstractExecutionScheduler implements ExecutionScheduler {

    @Setter
    protected ExecutorService executorService;

    protected void injectExecutorService(List<? extends StageTask> tasks) {
        for (var task : tasks) {
            if (task instanceof ExecutorServiceAware aware)
                aware.setExecutorService(executorService);
        }
    }
}
