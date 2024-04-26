package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestReportService extends ProjectLinkedService<TestReport, TestReportModel, Long> {

    public Optional<TestReportModel> findLastModel() {
        var models = findAllModels();
        return models.isEmpty()
                ? Optional.empty()
                : Optional.of(models.get(models.size() - 1));
    }
}
