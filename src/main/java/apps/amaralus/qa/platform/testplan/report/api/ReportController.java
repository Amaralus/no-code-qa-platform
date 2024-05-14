package apps.amaralus.qa.platform.testplan.report.api;

import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import apps.amaralus.qa.platform.testplan.report.TestReportService;
import apps.amaralus.qa.platform.testplan.report.model.TestReportModel;
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
    public List<TestReportModel> findAll(@PathVariable String project,
                                         @PathVariable Long testPlanId) {
        return reportService.findAllModelsByTestPlanId(testPlanId);
    }

    @GetMapping("/last")
    @InterceptProjectId
    public Object findLastReport(@PathVariable String project,
                                 @PathVariable Long testPlanId) {
        Optional<TestReportModel> optional = reportService.findLastModel(testPlanId);
        return optional.isPresent()
                ? optional.get()
                : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @InterceptProjectId
    public Object findReportById(@PathVariable String project,
                                 @PathVariable Long id,
                                 @PathVariable Long testPlanId) {
        Optional<TestReportModel> optional = reportService.findModelById(id);
        return optional.isPresent()
                ? optional.get()
                : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @InterceptProjectId
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable String project,
                             @PathVariable Long id,
                             @PathVariable String testPlanId) {
        reportService.delete(id);
    }

}
