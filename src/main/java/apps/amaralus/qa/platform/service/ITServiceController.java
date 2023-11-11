package apps.amaralus.qa.platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/it-services")
@RequiredArgsConstructor
@Validated
public class ITServiceController {

    private final ITServiceService itServiceService;

    @GetMapping
    public ResponseEntity<List<ITService>> findAll(@RequestParam String project) {
        return ResponseEntity.ok(itServiceService.findAllByProject(project));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ITService> createService(@Valid @RequestBody ITService itService) {
        return ResponseEntity.ok(itServiceService.createService(itService));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ITService> updateService(@PathVariable Long id, @RequestBody ITService itService) {
        return ResponseEntity.ok(itServiceService.updateService(id, itService));
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        itServiceService.deleteService(id);
    }
}
