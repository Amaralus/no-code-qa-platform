package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.schedule.ExecutionScheduler;
import apps.amaralus.qa.platform.testcase.TestCaseModel;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import apps.amaralus.qa.platform.testplan.TestPlanModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestPlanFactory {

    private final ExecutionScheduler sequentialExecutionScheduler;
    private final ExecutionScheduler parallelExecutionScheduler;
    private final TestCaseService testCaseService;
    private final TestCaseFactory testCaseFactory;

    public ExecutableTestPlan produce(TestPlanModel testPlanModel) {

        var testCases = testCaseService.findAllByProject(testPlanModel.getProject())
                .stream()
                .filter(this::allAutoStepsFilter)
                .map(testCaseFactory::produce)
                .toList();

        var executionGraph = getScheduler(testPlanModel.getExecutionProperties().parallelExecution())
                .schedule(testCases);

        return new ExecutableTestPlan(executionGraph, testCases);
    }

    private boolean allAutoStepsFilter(TestCaseModel testCaseModel) {
        if (testCaseModel.getTestSteps().isEmpty())
            return false;

        return testCaseModel.getTestSteps().stream()
                .map(testStep -> testStep.getStepExecutionProperties().getActionType())
                .allMatch(actionType -> actionType != ActionType.NONE);
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
