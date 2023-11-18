package apps.amaralus.qa.platform.placeholder.controller;

import apps.amaralus.qa.platform.placeholder.service.PlaceholderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/placeholders")
@RequiredArgsConstructor
public class PlaceholderController {

    private final PlaceholderService placeholderService;

    @GetMapping
    public Object getValueByPlaceholder(@RequestParam String placeholder, @RequestParam String project) {
        return placeholderService.getByPlaceholder(placeholder, project);
    }
}
