package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.Cancelable;
import apps.amaralus.qa.platform.runtime.execution.Executable;
import apps.amaralus.qa.platform.runtime.report.ReportSupplier;
import apps.amaralus.qa.platform.runtime.report.TestReport;
import apps.amaralus.qa.platform.runtime.report.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

import static apps.amaralus.qa.platform.runtime.TestState.CREATED;

@RequiredArgsConstructor
public abstract class ExecutableTestSupport implements Executable, Cancelable, ReportSupplier {
    @Getter
    protected final TestInfo testInfo;
    protected final Timer timer = new Timer();
    protected final AtomicBoolean canceled = new AtomicBoolean();
    @Getter
    protected String resultMessage = "";
    @Setter
    @Getter
    protected TestState state = CREATED;

    @Override
    public void cancel() {
        canceled.set(true);
    }

    @Override
    public boolean isCanceled() {
        return canceled.get();
    }

    @Override
    public TestReport getReport() {
        return new TestReport(testInfo, state, resultMessage, timer.getElapsedAsLocalTime());
    }
}
