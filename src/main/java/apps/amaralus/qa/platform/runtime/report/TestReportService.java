package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.context.DefaultProjectContext;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestReportService extends ProjectLinkedService<TestReport, TestReportModel, Long> {

    @Override
    public @NotNull TestReport create(TestReport entity) {
        //todo потому что теряется контекст
        projectContext = new DefaultProjectContext().setProjectId(entity.getTestInfo().project());
        return super.create(entity);
    }

    public Optional<TestReportModel> findLastModel() {
        var models = findAllModels();
        return models.isEmpty()
                ? Optional.empty()
                : Optional.of(models.get(models.size() - 1));
    }
}
