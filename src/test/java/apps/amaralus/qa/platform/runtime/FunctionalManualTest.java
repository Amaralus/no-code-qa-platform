package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.alias.AliasService;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;
import apps.amaralus.qa.platform.project.ProjectService;
import apps.amaralus.qa.platform.project.api.Project;
import apps.amaralus.qa.platform.project.context.DefaultProjectContext;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.execution.ExecutionProperties;
import apps.amaralus.qa.platform.runtime.execution.StepExecutionProperties;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionModel;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionRepository;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionModel;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionRepository;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import apps.amaralus.qa.platform.testcase.model.TestStep;
import apps.amaralus.qa.platform.testplan.TestPlan;
import apps.amaralus.qa.platform.testplan.TestPlanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static apps.amaralus.qa.platform.testcase.action.asserts.Assertion.ASSERT_EQUALS;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для ручной проверки всего и вся, по умолчанию выключен в сборке,
 * при ручном запуске - запускается
 */
@SpringBootTest
@Slf4j
@Disabled
@SuppressWarnings("all")
class FunctionalManualTest {

    @Autowired
    TestPlanService testPlanService;
    @Autowired
    ExecutionManager executionManager;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    DebugActionRepository debugActionRepository;
    @Autowired
    AssertActionRepository assertActionRepository;
    @Autowired
    PlaceholderResolver placeholderResolver;
    @Autowired
    DatasetService datasetService;
    @Autowired
    AliasService aliasService;
    @Autowired
    ProjectService projectService;
    @Autowired
    FolderService folderService;
    @Autowired
    ProjectContext projectContext;

    Project project;


    @BeforeEach
    void before() {
        ((DefaultProjectContext) projectContext).setProjectId("myProject");
        debugActionRepository.deleteAll();
        assertActionRepository.deleteAll();
        projectService.delete("myProject");
        project = projectService.create(new Project("myProject", "My Project", null, 0, 0));
    }

    @Test
    void runtime() throws InterruptedException {

        createTestCase(false, "TestCase1", 0L, 4, null);
        createTestCase(false, "TestCase2", 0L, 4, null);

        var testPlan = new TestPlan();
        testPlan.setName("My test plan");
        testPlan.setExecutionProperties(new ExecutionProperties(false));
        testPlan = testPlanService.create(testPlan);

        executionManager.run(testPlan.getId());
//        Thread.sleep(4000);
//        executionManager.stop(testPlan.getId());
        Thread.sleep(2000);

        assertTrue(true);
    }

    @Test
    void placeholders() {
        var project = projectService.create(new Project("myProject", null, null, 0L, 0L));
        datasetService.setVariable(folderService.findById(project.getRootFolder()).get().getDataset(), "var", "val");
        log.info("project {}", project);

        // act
        var resolve = placeholderResolver
                .resolve(Placeholder.parse("{{folder#18:var}}"));
        log.info("resolved: {}", resolve);

        projectService.delete("myProject");
        assertTrue(true);
    }

    @Test
    void runtimePlaceholders() throws InterruptedException {
        createTestCase(false, "Runtime placeholders case", project.getRootFolder(), 1,
                "resolved msg = {{project:var}}");

        datasetService.setVariable(project.getDataset(), "var", "val");

        var testPlan = new TestPlan();
        testPlan.setName("My test plan");
        testPlan.setExecutionProperties(new ExecutionProperties(false));
        testPlan = testPlanService.create(testPlan);

        executionManager.run(testPlan.getId());

        Thread.sleep(1000);

        assertTrue(true);
    }

    @Test
    void assertsRuntime() throws InterruptedException {

        var testCase = new TestCaseModel();
        testCase.setProject("myProject");
        testCase.setName("Assert case");
        testCase.setFolder(project.getRootFolder());
        testCase.setExecutionProperties(new ExecutionProperties(false));

        var step = new TestStep();
        step.setName("Assert that step");
        step.setStepExecutionProperties(new StepExecutionProperties(1, ActionType.ASSERT));
        testCase.setTestSteps(List.of(step));
        testCaseRepository.save(testCase);

        assertActionRepository.save(
                new AssertActionModel(1L, "myProject", ASSERT_EQUALS, "lol val", "lol {{project:var}}"));

        datasetService.setVariable(project.getDataset(), "var", "val");

        var testPlan = new TestPlan();
        testPlan.setName("My test plan");
        testPlan.setExecutionProperties(new ExecutionProperties(false));
        testPlan = testPlanService.create(testPlan);

        executionManager.run(testPlan.getId());

        Thread.sleep(1000);

        assertTrue(true);
    }

    void createTestCase(boolean parallelExecution, String name, Long folder, int stepCount, String debugMessage) {
        var testCase = new TestCaseModel();
        testCase.setProject("myProject");
        testCase.setName(name);
        testCase.setFolder(folder);
        testCase.setExecutionProperties(new ExecutionProperties(parallelExecution));

        var steps = new ArrayList<TestStep>();
        for (int i = 0; i < stepCount; i++) {
            var testStep = createTestStep(i + 1, debugMessage);
            steps.add(testStep);
        }

        testCase.setTestSteps(steps);
        testCaseRepository.save(testCase);
    }

    TestStep createTestStep(int iteration, String debugMessage) {
        var step = new TestStep();
        step.setName("step" + iteration);
        step.setStepExecutionProperties(new StepExecutionProperties(iteration, ActionType.DEBUG));

        debugMessage = debugMessage == null ? step.getName() + " executed" : debugMessage;
        debugActionRepository.save(new DebugActionModel((long) iteration, "myProject", debugMessage));

        return step;
    }
}