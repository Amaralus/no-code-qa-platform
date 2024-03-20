package apps.amaralus.qa.platform.project.api;

import apps.amaralus.qa.platform.project.ProjectService;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import static apps.amaralus.qa.platform.common.api.Routes.PROJECTS;

@RestController
@RequestMapping(PROJECTS)
@RequiredArgsConstructor
@Validated
public class ProjectController extends ProjectContextLinked {

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

    @PatchMapping("/{project}/description")
    public Project updateDescription(@PathVariable String project, @RequestParam String description) {
        return projectService.updateDescription(project, description);
    }

    @PatchMapping("/{project}/name")
    public Project updateName(@PathVariable String project, @RequestParam @NotBlank String name) {
        return projectService.updateName(project, name);
    }

    @DeleteMapping("/{project}")
    @InterceptProjectId
    public void deleteProject(@PathVariable String project) {
        projectService.delete(project);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAll() {
        var result = projectService.findAll();

        if (result.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        return result;
    }
}
