package apps.amaralus.qa.platform.runtime.execution.schedule;

import apps.amaralus.qa.platform.runtime.execution.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SequentialExecutionScheduler extends AbstractExecutionScheduler {

    @Override
    public ExecutionGraph schedule(List<? extends StageTask> tasks) {
        return schedule(tasks, new SimpleTask(), new SimpleTask());
    }

    @Override
    public ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask) {
        injectExecutorService(tasks);

        var stages = tasks.stream()
                .map(DefaultStage::new)
                .<LinkedList<Stage>>collect(LinkedList::new, List::add, List::addAll);

        stages.addFirst(new DefaultStage(initialTask));
        stages.addLast(new DefaultStage(finalTask));

        for (int i = 0; i < stages.size() - 1 ; i++) {
            var first = stages.get(i);
            var second = stages.get(i + 1);
            Stage.link(first, second);
        }

        return new ExecutionGraph(stages);
    }
}
