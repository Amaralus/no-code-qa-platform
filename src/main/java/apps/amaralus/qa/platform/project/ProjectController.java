package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.project.model.api.Project;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/api/project", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody @Valid Project project) {
        try {
            projectService.create(project);
        } catch (IllegalArgumentException e) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e);
        }
    }
}
