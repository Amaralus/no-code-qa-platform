package apps.amaralus.qa.platform.itservice;

import apps.amaralus.qa.platform.itservice.model.ITService;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static apps.amaralus.qa.platform.common.api.Routes.IT_SERVICES;

@RestController
@RequestMapping(IT_SERVICES)
@RequiredArgsConstructor
@Validated
public class ITServiceController {

    private final ITServiceService itServiceService;

    @GetMapping
    @InterceptProjectId
    public List<ITService> findAll(@PathVariable String project) {
        return itServiceService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @InterceptProjectId
    public ITService createService(@PathVariable String project, @Valid @RequestBody ITService itService) {
        return itServiceService.create(itService);
    }

    @DeleteMapping("/{id}")
    @InterceptProjectId
    public void deleteService(@PathVariable String project, @PathVariable Long id) {
        itServiceService.delete(id);
    }
}
