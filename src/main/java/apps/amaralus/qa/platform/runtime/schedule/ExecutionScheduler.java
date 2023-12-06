package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.execution.ExecutionGraph;
import apps.amaralus.qa.platform.runtime.execution.RuntimeExecutorAware;
import apps.amaralus.qa.platform.runtime.execution.StageTask;

import java.util.List;

public interface ExecutionScheduler extends RuntimeExecutorAware {

    ExecutionGraph schedule(List<? extends StageTask> tasks);

    ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask);
}
