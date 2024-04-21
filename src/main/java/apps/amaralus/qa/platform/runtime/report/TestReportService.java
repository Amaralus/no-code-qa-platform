package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.context.DefaultProjectContext;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.runtime.report.api.Report;
import apps.amaralus.qa.platform.runtime.report.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestReportService extends ProjectLinkedService<TestReport, TestReportModel, Long> {

    private final ReportMapper reportMapper;
    private TestInfo testInfo;

    public TestReport create(TestReport entity, TestInfo testInfo) {
        projectContext = new DefaultProjectContext().setProjectId(testInfo.project());
        this.testInfo = testInfo;
        return super.create(entity);
    }

    protected void beforeCreate(TestReportModel model) {
        model.setTestPlanId(testInfo.id());
        model.setName(testInfo.getTestName());
        super.beforeCreate(model);
    }

    public Report findReportById(Long id) {
        var reportModel = findModelById(id)
                .orElseThrow(() -> new EntityNotFoundException(TestReportModel.class, id));
        return reportMapper.toEntity(reportModel);
    }

    public List<Report> findAllReports() {
        return reportMapper.toEntityList(findAllModels());
    }

    public Optional<Report> findLastModel() {
        var models = findAllModels();
        return models.isEmpty()
                ? Optional.empty()
                : Optional.of(reportMapper.toEntity(models.get(models.size() - 1)));
    }
}
