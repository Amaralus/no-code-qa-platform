package apps.amaralus.qa.platform.dataset;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Dataset> createDataset(@RequestBody Dataset dataset) {
        return ResponseEntity.ok(datasetService.create(dataset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dataset> findById(@PathVariable Long id) {
        return ResponseEntity.ok(datasetService.getById(id));
    }

    @GetMapping("alias")
    public ResponseEntity<Dataset> findByAlias(@RequestParam String alias) {
        return ResponseEntity.ok(datasetService.getByAlias(alias));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dataset> updateDataset(@PathVariable Long id, @RequestBody Dataset dataset) {
        return ResponseEntity.ok(datasetService.updateDataset(id, dataset));
    }
}
