package apps.amaralus.qa.platform.environment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
@RequiredArgsConstructor
@Validated
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @GetMapping
    public List<Environment> findAll(@RequestParam String project) {
        return environmentService.findAllByProject(project);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Environment createEnvironment(@Valid @RequestBody Environment environment) {
        return environmentService.createEnvironment(environment);
    }

    @PatchMapping("/{id}")
    public Environment updateEnvironment(@PathVariable Long id, @RequestBody Environment environment) {
        return environmentService.updateEnvironment(id, environment);
    }

    @DeleteMapping("/{id}")
    public void deleteEnvironment(@PathVariable Long id) {
        environmentService.deleteEnvironment(id);
    }
}
