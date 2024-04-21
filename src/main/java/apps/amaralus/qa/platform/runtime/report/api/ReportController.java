package apps.amaralus.qa.platform.runtime.report.api;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import apps.amaralus.qa.platform.runtime.report.TestReportModel;
import apps.amaralus.qa.platform.runtime.report.TestReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports/{project}")
public class ReportController extends ProjectContextLinked {

    private final TestReportService reportService;

    @GetMapping
    @InterceptProjectId
    public List<Report> findAll(@PathVariable String project) {
        return reportService.findAllReports();
    }

    @GetMapping("/last")
    @InterceptProjectId
    public Object findLastReport(@PathVariable String project) {
        Optional<Report> optional = reportService.findLastModel();
        return optional.isPresent()
                ? optional.get()
                : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @InterceptProjectId
    public Report findReportById(@PathVariable String project, @PathVariable Long id) {
        return reportService.findReportById(id);
    }

    @InterceptProjectId
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable String project,
                             @PathVariable Long id) {
        reportService.delete(id);
    }

}
