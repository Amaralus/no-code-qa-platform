package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.testplan.TestPlan;
import apps.amaralus.qa.platform.testplan.TestPlanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static apps.amaralus.qa.platform.runtime.TestState.*;

@Service
@AllArgsConstructor
@Slf4j
public class ExecutionManager {

    private static final String NOT_NULL_MESSAGE = "testPlanId must not be null!";

    private final TestPlanFactory testPlanFactory;
    private final TestPlanService testPlanService;
    private final Map<Long, ExecutableTestPlan> runningTestPlans = new ConcurrentHashMap<>();

    public void run(@NotNull Long testPlanId) {
        Assert.notNull(testPlanId, NOT_NULL_MESSAGE);
        var testPlan = runningTestPlans.compute(testPlanId, this::rerun);
        log.info("Running test plan {}", testPlan.getTestInfo());
        testPlan.execute();
    }

    public void stop(@NotNull Long testPlanId) {
        Assert.notNull(testPlanId, NOT_NULL_MESSAGE);
        runningTestPlans.computeIfPresent(testPlanId, (key, testPlan) -> {
            log.info("Stopping test plan {} execution", testPlan.getTestInfo());
            finishExecution(testPlan);
            return null;
        });
    }

    public TestState getTestPlanState(@NotNull Long testPlanId) {
        Assert.notNull(testPlanId, NOT_NULL_MESSAGE);
        var testPlan = runningTestPlans.get(testPlanId);
        return testPlan == null ? UNKNOWN : testPlan.getState();
    }

    private ExecutableTestPlan rerun(long testPlanId, ExecutableTestPlan runningTestPlan) {
        if (runningTestPlan != null)
            finishExecution(runningTestPlan);

        var testPlan = testPlanService.findById(testPlanId)
                .orElseThrow(() -> new EntityNotFoundException(TestPlan.class, testPlanId));
        var executableTestPlan = testPlanFactory.produce(testPlan);
        executableTestPlan.setFinishCallback(this::testPlanFinishCallback);

        return executableTestPlan;
    }

    private void testPlanFinishCallback(ExecutableTestPlan testPlan) {
        runningTestPlans.remove(testPlan.getTestInfo().id());
        finishExecution(testPlan);
    }

    private void finishExecution(ExecutableTestPlan testPlan) {
        if (testPlan.getState() == CREATED || testPlan.getState() == RUNNING)
            testPlan.cancel();
        log.info("Test plan {} execution finished", testPlan.getTestInfo());
        // todo save report
        var report = testPlan.getReport();
        log.debug("Test plan {} report:\n{}", testPlan.getTestInfo(), report);
    }
}
