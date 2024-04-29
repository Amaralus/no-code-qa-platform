package apps.amaralus.qa.platform.testplan.report;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import apps.amaralus.qa.platform.testplan.report.model.TestReportModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestReportService extends ProjectLinkedService<TestReport, TestReportModel, Long> {

    public List<TestReportModel> findAllModelsByTestPlanId(Long testPlanId) {
        return findAllModels().stream()
                .filter(testReportModel -> testPlanId.equals(testReportModel.getTestPlanId()))
                .toList();
    }

    public Optional<TestReportModel> findLastModel(Long testPlanId) {
        var models = findAllModelsByTestPlanId(testPlanId);
        return models.isEmpty()
                ? Optional.empty()
                : Optional.of(models.get(models.size() - 1));
    }
}
