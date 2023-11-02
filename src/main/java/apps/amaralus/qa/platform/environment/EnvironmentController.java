package apps.amaralus.qa.platform.environment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/environments")
@RequiredArgsConstructor
@Validated
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createEnvironment(@Valid @RequestBody Environment environment) {
        environmentService.createEnvironment(environment);
    }

    @PatchMapping("/{id}")
    public void updateEnvironment(@PathVariable Long id, @RequestBody Environment environment) {
        environmentService.updateEnvironment(id, environment);
    }

    @DeleteMapping("/{id}")
    public void deleteEnvironment(@PathVariable Long id) {
        environmentService.deleteEnvironment(id);
    }
}
