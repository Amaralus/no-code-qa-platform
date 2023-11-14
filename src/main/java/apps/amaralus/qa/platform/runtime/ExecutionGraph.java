package apps.amaralus.qa.platform.runtime;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExecutionGraph implements Executable {

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
}
