package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.*;

import java.util.LinkedList;
import java.util.List;

public class ParallelExecutionScheduler {

    public ExecutionGraph schedule(List<? extends StageTask> tasks) {
        return schedule(tasks, new TaskStub(), new TaskStub());
    }

    public ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask) {
        var initialStage = new DefaultStage(initialTask);
        var finalStage = new DefaultStage(finalTask);

        var stages = tasks.stream()
                .map(DefaultStage::new)
                .<LinkedList<Stage>>collect(LinkedList::new, List::add, List::addAll);

        linkInputs(stages, initialStage);
        linkOutputs(stages, finalStage);

        stages.addFirst(initialStage);
        stages.addLast(finalStage);

        return new ExecutionGraph(stages);
    }

    private void linkInputs(List<? extends Stage> stages, Stage initialStage) {
        stages.stream()
                .filter(stage -> stage.inputsCount() == 0)
                .forEach(stage -> Stage.link(initialStage, stage));
    }

    private void linkOutputs(List<? extends Stage> stages, Stage finalStage) {
        stages.stream()
                .filter(stage -> stage.outputsCount() == 0)
                .forEach(stage -> Stage.link(stage, finalStage));
    }
}
