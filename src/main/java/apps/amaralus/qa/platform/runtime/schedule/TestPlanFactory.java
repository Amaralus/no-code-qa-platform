package apps.amaralus.qa.platform.runtime.schedule;

import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestCase;
import apps.amaralus.qa.platform.runtime.execution.ExecutableTestPlan;
import apps.amaralus.qa.platform.runtime.execution.SimpleTask;
import apps.amaralus.qa.platform.runtime.execution.context.TestContext;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.resolve.PlaceholderResolvingContextFactory;
import apps.amaralus.qa.platform.testcase.TestCase;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import apps.amaralus.qa.platform.testplan.AutomationDegree;
import apps.amaralus.qa.platform.testplan.TestPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static apps.amaralus.qa.platform.runtime.action.ActionType.NONE;
import static apps.amaralus.qa.platform.testplan.AutomationDegree.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestPlanFactory {

    private final ExecutionScheduler sequentialExecutionScheduler;
    private final ExecutionScheduler parallelExecutionScheduler;
    private final PlaceholderResolvingContextFactory resolvingContextFactory;
    private final PlaceholderGeneratorsProvider placeholderGeneratorsProvider;
    private final TestCaseService testCaseService;
    private final TestCaseFactory testCaseFactory;

    public ExecutableTestPlan produce(TestPlan testPlan) {

        // временно до добавления фичи для работы с полуавтоматическими и ручными тест планами
        if (testPlan.getAutomationDegree() != AUTO) {
            log.warn("{} automation degree is not supported, automation degree of test plan {} will be changed to AUTO",
                    testPlan.getId(),
                    testPlan.getAutomationDegree());
            testPlan.setAutomationDegree(AUTO);
        }

        var testCases = testCaseService.findAll()
                .stream()
                .filter(testCase -> determineAutomationDegree(testCase) == testPlan.getAutomationDegree())
                .map(testCaseFactory::produce)
                .toList();

        var executableTestPlan = buildExecutableTestPlan(testPlan, testCases);

        var contextFactory = resolvingContextFactory.createForTestPlan(testPlan);
        initializeTestContext(executableTestPlan, contextFactory);

        return executableTestPlan;
    }

    private AutomationDegree determineAutomationDegree(TestCase testCase) {
        long count = testCase.getTestSteps().stream()
                .filter(testStep -> testStep.getStepExecutionProperties().getActionType() == NONE)
                .count();

        if (count == testCase.getTestSteps().size())
            return MANUAL;
        else if (count == 0)
            return AUTO;
        else
            return SEMI_AUTO;
    }

    private ExecutableTestPlan buildExecutableTestPlan(TestPlan testPlan, List<ExecutableTestCase> testCases) {
        var executableTestPlan = new ExecutableTestPlan(new TestInfo(testPlan.getId(), testPlan.getName()));

        var executionGraph = getScheduler(testPlan.getExecutionProperties().parallelExecution())
                .schedule(testCases, new SimpleTask(), new SimpleTask(executableTestPlan::executionGraphFinishedCallback));
        executableTestPlan.setExecutionGraph(executionGraph);

        return executableTestPlan;
    }

    private void initializeTestContext(ExecutableTestPlan testPlan, PlaceholderResolvingContextFactory.Factory contextFactory) {
        for (var testCase : testPlan.getTestCases()) {
            var resolvingContext = contextFactory.produce(testCase.getTestInfo().id());
            for (var testStep : testCase.getTestSteps())
                testStep.setTestContext(
                        new TestContext(
                                testPlan.getTestInfo(),
                                testCase.getTestInfo(),
                                testStep.getTestInfo(),
                                new PlaceholderResolver(resolvingContext, placeholderGeneratorsProvider),
                                contextFactory.getTestCaseDataset()
                        ));
        }
    }

    private ExecutionScheduler getScheduler(boolean isParallel) {
        return isParallel ? parallelExecutionScheduler : sequentialExecutionScheduler;
    }
}
