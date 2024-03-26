package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.ExecutionGraph;
import apps.amaralus.qa.platform.runtime.execution.ExecutionGraphDelegate;
import apps.amaralus.qa.platform.runtime.report.ReportSupplier;
import apps.amaralus.qa.platform.runtime.report.TestReport;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;

import static apps.amaralus.qa.platform.runtime.TestState.*;

public class ExecutableTestPlan extends ExecutableTestSupport implements ExecutionGraphDelegate {
    @Setter
    private ExecutionGraph executionGraph;
    @Setter
    private Consumer<ExecutableTestPlan> finishCallback;

    public ExecutableTestPlan(TestInfo testInfo) {
        super(testInfo);
    }

    @Override
    public void execute() {
        if (isCanceled())
            return;

        timer.start();
        setState(RUNNING);
        executionGraph.execute();
    }

    @Override
    public void cancel() {
        super.cancel();
        setState(CANCELED);
        executionGraph.cancel();
        timer.stop();
    }

    @Override
    public void executionGraphFinishedCallback() {
        setState(COMPLETED);
        timer.stop();
        finishCallback.accept(this);
    }

    public List<ExecutableTestCase> getTestCases() {
        return executionGraph.getTasks(ExecutableTestCase.class);
    }

    @Override
    public TestReport getReport() {
        var report = super.getReport();
        report.setSubReports(getTestCases().stream()
                .map(ReportSupplier::getReport)
                .toList());
        return report;
    }
}
