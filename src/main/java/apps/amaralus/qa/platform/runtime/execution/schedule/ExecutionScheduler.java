package apps.amaralus.qa.platform.runtime.execution.schedule;

import apps.amaralus.qa.platform.runtime.execution.ExecutionGraph;
import apps.amaralus.qa.platform.runtime.execution.ExecutorServiceAware;
import apps.amaralus.qa.platform.runtime.execution.StageTask;

import java.util.List;

public interface ExecutionScheduler extends ExecutorServiceAware {

    ExecutionGraph schedule(List<? extends StageTask> tasks);

    ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask);
}
