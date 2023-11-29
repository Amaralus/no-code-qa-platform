package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.execution.schedule.ExecutionScheduler;
import apps.amaralus.qa.platform.testcase.ExecutionActionService;
import apps.amaralus.qa.platform.testcase.TestCaseModel;
import apps.amaralus.qa.platform.testcase.TestStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TestCaseFactory {

    private final ExecutionScheduler sequentialExecutionScheduler;
    private final ExecutionScheduler parallelExecutionScheduler;
    private final ExecutionActionService executionActionService;

    public ExecutableTestCase produce(TestCaseModel testCaseModel) {

        var testSteps = new ArrayList<ExecutableTestStep>();
        for (int i = 0; i < testCaseModel.getTestSteps().size(); i++)
            testSteps.add(produce(testCaseModel.getTestSteps().get(i), i));

        var testCase = new ExecutableTestCase(new TestCaseInfo(testCaseModel));
        testCase.setTestSteps(testSteps);
        var graph = getScheduler(testCaseModel.getExecutionProperties().parallelExecution())
                .schedule(testSteps, new SimpleTask(), new SimpleTask(testCase::stepsGraphFinishedCallback));
        testCase.setStepsExecutionGraph(graph);

        return testCase;
    }

    private ExecutableTestStep produce(TestStep testStep, int orderNumber) {
        var executionProperties = testStep.getStepExecutionProperties();
        var stepAction = executionActionService.getById(executionProperties.getExecutionAction())
                .toStepAction();

        var executableTestStep = new ExecutableTestStep(new TestStepInfo(orderNumber, testStep.getName()), stepAction);
        executableTestStep.timeout(executionProperties.getTimeout(), executionProperties.getTimeUnit());

        return executableTestStep;
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
