package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.schedule.ExecutionScheduler;
import apps.amaralus.qa.platform.testcase.TestCase;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import apps.amaralus.qa.platform.testplan.TestPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestPlanFactory {

    private final ExecutionScheduler sequentialExecutionScheduler;
    private final ExecutionScheduler parallelExecutionScheduler;
    private final TestCaseService testCaseService;
    private final TestCaseFactory testCaseFactory;

    public ExecutableTestPlan produce(TestPlan testPlan) {

        var testCases = testCaseService.findAll()
                .stream()
                .filter(this::allAutoStepsFilter)
                .map(testCaseFactory::produce)
                .toList();

        var executableTestPlan = new ExecutableTestPlan(new TestInfo(testPlan.getId(), testPlan.getName()));

        var executionGraph = getScheduler(testPlan.getExecutionProperties().parallelExecution())
                .schedule(testCases, new SimpleTask(), new SimpleTask(executableTestPlan::executionGraphFinishedCallback));
        executableTestPlan.setExecutionGraph(executionGraph);

        initializeTestContext(executableTestPlan);

        return executableTestPlan;
    }

    private boolean allAutoStepsFilter(TestCase testCase) {
        if (testCase.getTestSteps().isEmpty())
            return false;

        return testCase.getTestSteps().stream()
                .map(testStep -> testStep.getStepExecutionProperties().getActionType())
                .allMatch(actionType -> actionType != ActionType.NONE);
    }

    private void initializeTestContext(ExecutableTestPlan testPlan) {
        testPlan.getTestCases().forEach(testCase ->
                testCase.getTestSteps().forEach(testStep ->
                        testStep.setTestContext(
                                new TestContext(testPlan.getTestInfo(), testCase.testInfo, testStep.testInfo))));
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
