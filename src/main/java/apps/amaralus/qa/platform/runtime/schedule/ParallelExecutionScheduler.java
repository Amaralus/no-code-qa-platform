package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.execution.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ParallelExecutionScheduler extends AbstractExecutionScheduler {

    @Override
    public ExecutionGraph schedule(List<? extends StageTask> tasks) {
        return schedule(tasks, new SimpleTask(), new SimpleTask());
    }

    @Override
    public ExecutionGraph schedule(List<? extends StageTask> tasks, StageTask initialTask, StageTask finalTask) {
        injectExecutorService(tasks);

        var initialStage = new DefaultStage(initialTask);
        var finalStage = new DefaultStage(finalTask);

        var stagesMap = tasks.stream()
                .map(DefaultStage::new)
                .collect(Collectors.toMap(
                        stage -> stage.getStageTask().getTaskId(),
                        Function.identity()
                ));

        stagesMap.forEach((id, stage) -> {
            var taskProperties = stage.getStageTask().getExecutionProperties();
            taskProperties.getExecuteAfterTasks().forEach(previousStage -> Stage.link(stagesMap.get(previousStage), stage));
            taskProperties.getDependsFromTasks().forEach(previousStage -> Stage.link(stagesMap.get(previousStage), stage));
        });

        var stages = new LinkedList<>(stagesMap.values());

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
