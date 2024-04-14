package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@AllArgsConstructor
public class TestReportService extends ProjectLinkedService<TestReport, TestReportModel, Long> {

    public TestReportModel updateDescription(Long id, String description) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        var report = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Report not found by id " + id + "for project " + projectContext.getProjectId()));
        report.setDescription(description);

        return repository.save(report);
    }


    public TestReportModel updateName(Long id, String name) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);

        var report = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Report not found by id " + id + "for project " + projectContext.getProjectId()));
        report.setName(name);

        return repository.save(report);
    }
}
