package apps.amaralus.qa.platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/it-services")
@RequiredArgsConstructor
@Validated
public class ITServiceController {

    private final ITServiceService itServiceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createService(@Valid @RequestBody ITService itService) {
        itServiceService.createService(itService);
    }

    @PatchMapping("/{id}")
    public void updateService(@PathVariable Long id, @RequestBody ITService itService) {
        itServiceService.updateService(id, itService);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        itServiceService.deleteService(id);
    }
}
