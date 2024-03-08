package apps.amaralus.qa.platform.itservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<ITService> findAll(@RequestParam String project) {
        return itServiceService.findAllByProject(project);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ITService createService(@Valid @RequestBody ITService itService) {
        return itServiceService.createService(itService);
    }

    @PatchMapping("/{id}")
    public ITService updateService(@PathVariable Long id, @RequestBody ITService itService) {
        return itServiceService.updateService(id, itService);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        itServiceService.deleteService(id);
    }
}
