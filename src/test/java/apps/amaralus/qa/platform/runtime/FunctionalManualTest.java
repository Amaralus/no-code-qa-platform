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
import apps.amaralus.qa.platform.runtime.action.debug.DebugAction;
import apps.amaralus.qa.platform.runtime.action.debug.DebugActionRepository;
import apps.amaralus.qa.platform.testcase.TestCaseModel;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import apps.amaralus.qa.platform.testcase.TestStep;
import apps.amaralus.qa.platform.testplan.TestPlanModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для ручной проверки всего и вся, по умолчанию выключен в сборке,
 * при ручном запуске - запускается
 */
@SpringBootTest
@Slf4j
@Disabled
class FunctionalManualTest {

    @Autowired
    TestPlanFactory testPlanFactory;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    DebugActionRepository debugActionRepository;
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


    @Test
    void runtime() throws InterruptedException {

        ((DefaultProjectContext) projectContext).setProjectId("myProject");
        createTestCase(false, "TestCase1");
        createTestCase(false, "TestCase2");
        saveTestActions();

        var testPlan = new TestPlanModel();
        testPlan.setId(1L);
        testPlan.setName("My test plan");
        testPlan.setProject("myProject");
        testPlan.setExecutionProperties(new ExecutionProperties(false));
        var executableTestPlan = testPlanFactory.produce(testPlan);

        executableTestPlan.execute();
//        Thread.sleep(4000);
//        testPlan.cancel();
        Thread.sleep(2000);

        System.out.println(executableTestPlan.getReport());

        debugActionRepository.deleteAll();
        testCaseRepository.deleteAll();
        assertTrue(true);
    }

    @Test
    void placeholders() {
        ((DefaultProjectContext) projectContext).setProjectId("myProject");
        projectService.delete("myProject");

        var project = projectService.create(new Project("myProject", null, null, 0L, 0L));
        datasetService.setVariable(folderService.findById(project.getRootFolder()).get().getDataset(), "var", "val");
        log.info("project {}", project);

        // act
        var resolve = placeholderResolver
                .resolve(Placeholder.parse("{{folder#11:var}}"));
        log.info("resolved: {}", resolve);

        projectService.delete("myProject");
        assertTrue(true);
    }

    void createTestCase(boolean parallelExecution, String name) {
        var testCase = new TestCaseModel();
        testCase.setProject("myProject");
        testCase.setName(name);
        testCase.setExecutionProperties(new ExecutionProperties(parallelExecution));

        var step1 = new TestStep();
        step1.setName("step1");
        step1.setStepExecutionProperties(new StepExecutionProperties(1, ActionType.DEBUG));

        var step2 = new TestStep();
        step2.setName("step2");
        step2.setStepExecutionProperties(new StepExecutionProperties(2, ActionType.DEBUG));

        var step3 = new TestStep();
        step3.setName("step3");
        step3.setStepExecutionProperties(new StepExecutionProperties(3, ActionType.DEBUG));

        var step4 = new TestStep();
        step4.setName("step4");
        step4.setStepExecutionProperties(new StepExecutionProperties(4, ActionType.DEBUG));

        testCase.setTestSteps(List.of(step1, step2, step3, step4));
        testCaseRepository.save(testCase);
    }

    void saveTestActions() {
        debugActionRepository.save(new DebugAction(1, "myProject", "step 11"));
        debugActionRepository.save(new DebugAction(2, "myProject", "step 22"));
        debugActionRepository.save(new DebugAction(3, "myProject", "step 33"));
        debugActionRepository.save(new DebugAction(4, "myProject", "step 44"));
    }
}