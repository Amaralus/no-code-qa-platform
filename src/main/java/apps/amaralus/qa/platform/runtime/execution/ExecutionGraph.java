package apps.amaralus.qa.platform.runtime.execution;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ExecutionGraph implements Executable, Cancelable {

    private final AtomicBoolean canceled = new AtomicBoolean();
    private final Stage initialStage;
    @Getter
    private final List<? extends Stage> stages;

    public ExecutionGraph(List<? extends Stage> stages) {
        this.stages = stages;
        this.initialStage = stages.get(0);
    }

    @Override
    public void execute() {
        initialStage.execute();
    }

    @Override
    public void cancel() {
        canceled.set(true);
        stages.forEach(Cancelable::cancel);
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }

    public List<StageTask> getTasks() {
        return getStages().stream()
                .map(Stage::getStageTask)
                .toList();
    }

    public <T extends StageTask> List<T> getTasks(Class<T> taskClass) {
        return getStages().stream()
                .map(Stage::getStageTask)
                .filter(task -> task.getClass() == taskClass)
                .map(taskClass::cast)
                .toList();
    }
}
