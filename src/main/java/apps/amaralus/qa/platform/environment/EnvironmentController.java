package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static apps.amaralus.qa.platform.common.api.Routes.ENVIRONMENTS;

@RestController
@RequestMapping(ENVIRONMENTS)
@RequiredArgsConstructor
@Validated
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @GetMapping
    @InterceptProjectId
    public List<Environment> findAll(@PathVariable String project) {
        return environmentService.findAllByProject(project);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @InterceptProjectId
    public Environment createEnvironment(@PathVariable String project, @Valid @RequestBody Environment environment) {
        return environmentService.createEnvironment(environment);
    }

    @PatchMapping("/{id}")
    @InterceptProjectId
    public Environment updateEnvironment(@PathVariable String project,
                                         @PathVariable Long id,
                                         @RequestBody Environment environment) {
        return environmentService.updateEnvironment(id, environment);
    }

    @DeleteMapping("/{id}")
    @InterceptProjectId
    public void deleteEnvironment(@PathVariable String project, @PathVariable Long id) {
        environmentService.deleteEnvironment(id);
    }
}
