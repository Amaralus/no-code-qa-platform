package apps.amaralus.qa.platform.dataset.controller;

import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/datasets")
@RequiredArgsConstructor
@Validated
public class DatasetController {

    private final DatasetService datasetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dataset createDataset(@RequestBody Dataset dataset) {
        return datasetService.create(dataset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dataset> findById(@PathVariable Long id) {
        return datasetService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/path")
    public ResponseEntity<Dataset> findByPath(@RequestParam String path, @RequestParam String project) {
        return datasetService.getByPath(path, project)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PatchMapping("/{id}")
    public Dataset updateDataset(@PathVariable Long id, @RequestBody Dataset dataset) {
        return datasetService.updateDataset(id, dataset);
    }
}
