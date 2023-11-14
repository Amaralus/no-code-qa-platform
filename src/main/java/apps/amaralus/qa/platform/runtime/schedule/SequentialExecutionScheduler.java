package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.*;

import java.util.LinkedList;
import java.util.List;

public class SequentialExecutionScheduler {

    public ExecutionGraph schedule(List<? extends StageTask> tasks) {
        return schedule(tasks, new TaskStub(), new TaskStub());
    }

    public ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask) {
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
