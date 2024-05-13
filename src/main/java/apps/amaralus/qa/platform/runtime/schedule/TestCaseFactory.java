package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.runtime.action.RuntimeActionFactory;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestCase;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestStep;
import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.testcase.model.TestCase;
import apps.amaralus.qa.platform.testcase.model.TestStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TestCaseFactory {

    private final ExecutionScheduler sequentialExecutionScheduler;
    private final ExecutionScheduler parallelExecutionScheduler;
    private final RuntimeActionFactory runtimeActionFactory;
    private final ProjectContext projectContext;

    public ExecutableTestCase produce(TestCase testCase) {

        var testSteps = new ArrayList<ExecutableTestStep>();
        for (int i = 0; i < testCase.getTestSteps().size(); i++)
            testSteps.add(produce(testCase.getTestSteps().get(i), i));

        var executableTestCase = new ExecutableTestCase(
                new TestInfo(testCase.getId(), testCase.getName(), projectContext.getProjectId()));
        var graph = getScheduler(testCase.getExecutionProperties().isParallelExecution())
                .schedule(testSteps, new SimpleTask(), new SimpleTask(executableTestCase::executionGraphFinishedCallback));
        executableTestCase.setExecutionGraph(graph);

        return executableTestCase;
    }

    private ExecutableTestStep produce(TestStep testStep, int orderNumber) {
        var executionProperties = testStep.getStepExecutionProperties();
        var stepAction = runtimeActionFactory.produceAction(executionProperties);

        var executableTestStep = new ExecutableTestStep(
                new TestInfo(orderNumber, testStep.getName(), projectContext.getProjectId()), stepAction);
        executableTestStep.timeout(executionProperties.getTimeout(), executionProperties.getTimeUnit());

        return executableTestStep;
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
