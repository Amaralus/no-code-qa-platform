package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.action.ActionsService;
import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.schedule.ExecutionScheduler;
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
    private final ActionsService actionsService;

    public ExecutableTestCase produce(TestCaseModel testCaseModel) {

        var testSteps = new ArrayList<ExecutableTestStep>();
        for (int i = 0; i < testCaseModel.getTestSteps().size(); i++)
            testSteps.add(produce(testCaseModel.getTestSteps().get(i), i));

        var testCase = new ExecutableTestCase(new TestInfo(testCaseModel.getId(), testCaseModel.getName()));
        var graph = getScheduler(testCaseModel.getExecutionProperties().parallelExecution())
                .schedule(testSteps, new SimpleTask(), new SimpleTask(testCase::executionGraphFinishedCallback));
        testCase.setExecutionGraph(graph);

        return testCase;
    }

    private ExecutableTestStep produce(TestStep testStep, int orderNumber) {
        var executionProperties = testStep.getStepExecutionProperties();
        var stepAction = actionsService.produceAction(executionProperties);

        var executableTestStep = new ExecutableTestStep(new TestInfo(orderNumber, testStep.getName()), stepAction);
        executableTestStep.timeout(executionProperties.getTimeout(), executionProperties.getTimeUnit());

        return executableTestStep;
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
