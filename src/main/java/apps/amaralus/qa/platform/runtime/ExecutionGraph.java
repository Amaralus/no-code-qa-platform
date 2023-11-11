package apps.amaralus.qa.platform.runtime;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExecutionGraph implements Executable {

    private final Stage startStage = new DefaultStage(new GraphTask());
    private final Stage finishStage = new DefaultStage(new GraphTask());
    private final List<? extends Stage> stages;

    public ExecutionGraph(List<? extends Stage> stages) {
        this.stages = stages;
        linkInputs();
        linkOutputs();
    }

    @Override
    public void execute() {
        startStage.execute();
    }

    private void linkInputs() {
        stages.stream()
                .filter(stage -> stage.inputsCount() == 0)
                .forEach(stage -> stage.addInput(startStage));
    }

    private void linkOutputs() {
        stages.stream()
                .filter(stage -> stage.outputsCount() == 0)
                .forEach(finishStage::addInput);
    }

    private static final class GraphTask implements StageTask {
        Runnable callback;

        @Override
        public void execute() {
            callback.run();
        }

        @Override
        public void setTaskFinishCallback(Runnable taskFinishCallback) {
            callback = taskFinishCallback;
        }
    }
}
