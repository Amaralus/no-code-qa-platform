package apps.amaralus.qa.platform.dataset.controller;

import apps.amaralus.qa.platform.dataset.service.DatasetService;
import apps.amaralus.qa.platform.dataset.dto.Dataset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public Dataset findById(@PathVariable Long id) {
        return datasetService.getById(id);
    }

    @GetMapping("/path")
    public Dataset findByPath(@RequestParam String path, @RequestParam String project) {
        return datasetService.getByPath(path, project);
    }

    @PatchMapping("/{id}")
    public Dataset updateDataset(@PathVariable Long id, @RequestBody Dataset dataset) {
        return datasetService.updateDataset(id, dataset);
    }
}
