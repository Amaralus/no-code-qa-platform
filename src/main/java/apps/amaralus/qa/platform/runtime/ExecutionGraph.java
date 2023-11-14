package apps.amaralus.qa.platform.runtime;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ExecutionGraph implements Executable, Cancelable {

    private final AtomicBoolean canceled = new AtomicBoolean();
    private final Stage initialStage;
    private final Stage finalStage;
    private final List<? extends Stage> stages;

    public ExecutionGraph(List<? extends Stage> stages) {
        this.stages = stages;
        this.initialStage = stages.get(0);
        this.finalStage = stages.get(stages.size() - 1);
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
}
