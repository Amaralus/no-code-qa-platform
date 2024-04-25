package apps.amaralus.qa.platform.runtime.report.api;

import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import apps.amaralus.qa.platform.runtime.report.TestReportModel;
import apps.amaralus.qa.platform.runtime.report.TestReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static apps.amaralus.qa.platform.common.api.Routes.REPORTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(REPORTS)
public class ReportController extends ProjectContextLinked {

    private final TestReportService reportService;

    @GetMapping
    @InterceptProjectId
    public List<TestReportModel> findAll(@PathVariable String project) {
        return reportService.findAllModels();
    }

    @GetMapping("/last")
    @InterceptProjectId
    public Object findLastReport(@PathVariable String project) {
        Optional<TestReportModel> optional = reportService.findLastModel();
        return optional.isPresent()
                ? optional.get()
                : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @InterceptProjectId
    public Object findReportById(@PathVariable String project, @PathVariable Long id) {
        Optional<TestReportModel> optional = reportService.findModelById(id);
        return optional.isPresent()
                ? optional.get()
                : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @InterceptProjectId
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable String project,
                             @PathVariable Long id) {
        reportService.delete(id);
    }

}
