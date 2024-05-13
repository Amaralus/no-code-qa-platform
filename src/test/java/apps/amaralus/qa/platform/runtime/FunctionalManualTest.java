package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.alias.AliasService;
import apps.amaralus.qa.platform.environment.itservice.ITServiceService;
import apps.amaralus.qa.platform.environment.itservice.model.ITService;
import apps.amaralus.qa.platform.environment.serviceapi.rest.RestCallModel;
import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;
import apps.amaralus.qa.platform.project.ProjectService;
import apps.amaralus.qa.platform.project.api.Project;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.execution.StepExecutionProperties;
import apps.amaralus.qa.platform.testcase.TestCaseRepository;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionModel;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionRepository;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionModel;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionRepository;
import apps.amaralus.qa.platform.testcase.model.TestCaseExecutionProperties;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import apps.amaralus.qa.platform.testcase.model.TestStep;
import apps.amaralus.qa.platform.testplan.TestPlan;
import apps.amaralus.qa.platform.testplan.TestPlanExecutionProperties;
import apps.amaralus.qa.platform.testplan.TestPlanService;
import apps.amaralus.qa.platform.testplan.report.TestReportService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static apps.amaralus.qa.platform.environment.serviceapi.rest.Method.GET;
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
    @Autowired
    TestReportService testReportService;
    @Autowired
    ITServiceService itServiceService;

    Project project;

    @BeforeEach
    void before() {
        // в каждый тестовый метод идет в разных потоках
        projectContext.setProjectId("myProject");
        debugActionRepository.deleteAll();
        assertActionRepository.deleteAll();
        projectService.delete("myProject");
        project = projectService.create(new Project("myProject", "My Project", null, 0, 0));
    }

    @Test
    void http() throws IOException {
        var itService = itServiceService.create(
                new ITService(0l, "github", null, "github.com", 443));

        var client = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .build();

        var callApi = new RestCallModel();
        callApi.setMethod(GET);
        callApi.setHttps(true);
        callApi.setPath("/amaralus");

//        var body = RequestBody.create(
//                MediaType.parse(callApi.getContentType().value()),
//                callApi.getBody());

        var urlBuilder = new HttpUrl.Builder()
                .scheme(callApi.isHttps() ? "https" : "http")
                .host(itService.getHost())
                .encodedPath(callApi.getPath())
                .port(itService.getPort());
        callApi.getQueryParams().entrySet()
                .forEach(entry -> urlBuilder.addQueryParameter(entry.getKey(), entry.getValue()));

        var call = client.newCall(new Request.Builder()
                .method(callApi.getMethod().name(), null)
                .headers(Headers.of(callApi.getHeaders()))
                .url(urlBuilder.build())
                .build());

        var response = call.execute();
        log.info("code: {}", response.code());
        log.info("content-type: {}", response.header("content-type"));
        var responceBody = response.body().string();
        log.info("body: {}", responceBody);
    }

    @Test
    void runtime() throws InterruptedException {
        projectContext.setProjectId("myProject");

        createTestCase(false, "TestCase1", 0L, 4, null);
        createTestCase(false, "TestCase2", 0L, 4, null);

        var testPlan = new TestPlan();
        testPlan.setName("My test plan");
        testPlan.setExecutionProperties(new TestPlanExecutionProperties(false, false));
        testPlan = testPlanService.create(testPlan);

        executionManager.run(testPlan.getId());
//        Thread.sleep(4000);
//        executionManager.stop(testPlan.getId());
        Thread.sleep(2000);
        System.out.println(testReportService.findAllModels());
        testReportService.deleteAllByProject();

        assertTrue(true);
    }

    @Test
    void placeholders() {
        projectContext.setProjectId("myProject");
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
        projectContext.setProjectId("myProject");
        createTestCase(false, "Runtime placeholders case", project.getRootFolder(), 1,
                "resolved msg = {{project:var}}");

        datasetService.setVariable(project.getDataset(), "var", "val");

        var testPlan = new TestPlan();
        testPlan.setName("My test plan");
        testPlan.setExecutionProperties(new TestPlanExecutionProperties(false, false));
        testPlan = testPlanService.create(testPlan);

        executionManager.run(testPlan.getId());

        Thread.sleep(1000);

        assertTrue(true);
    }

    @Test
    void assertsRuntime() throws InterruptedException {
        projectContext.setProjectId("myProject");

        var testCase = new TestCaseModel();
        testCase.setProject("myProject");
        testCase.setName("Assert case");
        testCase.setFolder(project.getRootFolder());
        testCase.setExecutionProperties(new TestCaseExecutionProperties(false, false));

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
        testPlan.setExecutionProperties(new TestPlanExecutionProperties(false, false));
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
        testCase.setExecutionProperties(new TestCaseExecutionProperties(parallelExecution, false));

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