package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final TestReportService reportService;

    @GetMapping("/{project}")
    @InterceptProjectId
    public List<TestReportModel> findAll(@PathVariable String project) {
        return reportService.findAllModels();
    }

    @InterceptProjectId
    @PatchMapping("/{project}/{id}/description")
    public TestReportModel updateDescription(@PathVariable String project,
                                             @PathVariable Long id,
                                             @RequestParam String description) {
        return reportService.updateDescription(id, description);
    }

    @InterceptProjectId
    @PatchMapping("/{project}/{id}/name")
    public TestReportModel updateName(@PathVariable String project,
                                      @PathVariable Long id,
                                      @RequestParam @NotBlank String name) {
        return reportService.updateName(id, name);
    }

    @InterceptProjectId
    @DeleteMapping("/{project}/{id}")
    public void deleteReport(@PathVariable String project,
                             @PathVariable Long id) {
        reportService.delete(id);
    }

}
