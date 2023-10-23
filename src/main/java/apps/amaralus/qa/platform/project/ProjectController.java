package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.project.model.api.Project;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@Valid @RequestBody Project project) {
        try {
            projectService.create(project);
        } catch (IllegalArgumentException e) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PatchMapping("/{id}/description")
    public void updateDescription(@PathVariable String id, @RequestParam String description) {
        projectService.updateDescription(id, description);
    }

    @PatchMapping("/{id}/name")
    public void updateName(@PathVariable String id, @RequestParam @NotBlank String name) {
        projectService.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAll() {
        var result = projectService.findAll();

        if (result.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        return result;
    }
}
