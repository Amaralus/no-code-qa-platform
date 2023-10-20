package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody @Valid Project project) {
        try {
            projectService.create(project);
        } catch (IllegalArgumentException e) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PatchMapping("/{name}/description")
    public void updateDescription(@PathVariable String name, @RequestParam String description) {
        projectService.updateDescription(name, description);
    }

    @DeleteMapping("/{name}")
    public void deleteProject(@PathVariable String name) {
        projectService.delete(name);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectModel> getAll() {
        return projectService.findAll();
    }
}
