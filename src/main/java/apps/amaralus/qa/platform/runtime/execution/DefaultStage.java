package apps.amaralus.qa.platform.runtime.execution;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultStage implements Stage {

    private final AtomicBoolean canceled = new AtomicBoolean();
    private final AtomicInteger inputCounter = new AtomicInteger();
    private final List<Stage> inputStages = new ArrayList<>();
    private final List<Stage> outputStages = new ArrayList<>();
    private final StageTask stageTask;

    public DefaultStage(StageTask stageTask) {
        this.stageTask = stageTask;
        stageTask.setTaskFinishCallback(this::taskFinishCallback);
    }

    @Override
    public void execute() {
        if (!isCanceled() && (inputStages.isEmpty() || inputCounter.incrementAndGet() == inputsCount()))
                stageTask.execute();
    }

    @Override
    public void cancel() {
        canceled.set(true);
        stageTask.cancel();
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }

    @Override
    public void taskFinishCallback() {
        inputCounter.set(0);
        outputStages.forEach(Stage::execute);
    }

    @Override
    public void addInput(@NotNull Stage stage) {
        Assert.notNull(stage, "Stage must not be null!");
        inputStages.add(stage);
    }

    @Override
    public void addOutput(@NotNull Stage stage) {
        Assert.notNull(stage, "Stage must not be null!");
        outputStages.add(stage);
    }

    @Override
    public int inputsCount() {
        return inputStages.size();
    }

    @Override
    public int outputsCount() {
        return outputStages.size();
    }
}
