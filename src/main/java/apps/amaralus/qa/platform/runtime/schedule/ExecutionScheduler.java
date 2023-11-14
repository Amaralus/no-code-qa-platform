package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.ExecutionGraph;
import apps.amaralus.qa.platform.runtime.ExecutorServiceAware;
import apps.amaralus.qa.platform.runtime.StageTask;

import java.util.List;

public interface ExecutionScheduler extends ExecutorServiceAware {

    ExecutionGraph schedule(List<? extends StageTask> tasks);

    ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask);
}
