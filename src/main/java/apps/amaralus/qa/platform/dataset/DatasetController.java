package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static apps.amaralus.qa.platform.common.api.Routes.DATASETS;

@RestController
@RequestMapping(DATASETS)
@RequiredArgsConstructor
@Validated
public class DatasetController extends ProjectContextLinked {

    private final DatasetService datasetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @InterceptProjectId
    public Dataset createDataset(@PathVariable String project, @RequestBody Dataset dataset) {
        return datasetService.create(dataset);
    }

    @GetMapping("/{id}")
    @InterceptProjectId
    public ResponseEntity<Dataset> findById(@PathVariable String project, @PathVariable Long id) {
        return datasetService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
