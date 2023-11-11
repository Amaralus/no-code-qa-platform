package apps.amaralus.qa.platform.dataset;

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

    @GetMapping("/alias")
    public Dataset findByAlias(@RequestParam String alias, @RequestParam String project) {
        return datasetService.getByAlias(alias, project);
    }

    @PatchMapping("/{id}")
    public Dataset updateDataset(@PathVariable Long id, @RequestBody Dataset dataset) {
        return datasetService.updateDataset(id, dataset);
    }
}
