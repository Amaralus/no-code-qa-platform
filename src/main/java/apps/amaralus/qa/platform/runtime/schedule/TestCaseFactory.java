package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.runtime.action.ActionsService;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestCase;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestStep;
import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.testcase.TestCase;
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

    public ExecutableTestCase produce(TestCase testCase) {

        var testSteps = new ArrayList<ExecutableTestStep>();
        for (int i = 0; i < testCase.getTestSteps().size(); i++)
            testSteps.add(produce(testCase.getTestSteps().get(i), i));

        var executableTestCase = new ExecutableTestCase(new TestInfo(testCase.getId(), testCase.getName()));
        var graph = getScheduler(testCase.getExecutionProperties().parallelExecution())
                .schedule(testSteps, new SimpleTask(), new SimpleTask(executableTestCase::executionGraphFinishedCallback));
        executableTestCase.setExecutionGraph(graph);

        return executableTestCase;
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
