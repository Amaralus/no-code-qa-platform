package apps.amaralus.qa.platform.placeholder.controller;

import apps.amaralus.qa.platform.placeholder.service.PlaceholderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/placeholders")
@RequiredArgsConstructor
public class PlaceholderController {

    private final PlaceholderService placeholderService;

    @GetMapping("/{project}/{placeholder}/resolve")
    public Object getResolvedValueByPlaceholder(@PathVariable String placeholder, @PathVariable String project) {
        return placeholderService.getResolveByPlaceholder(placeholder, project);
    }

    @GetMapping("/{project}/{placeholder}/raw")
    public Object getRawValueByPlaceholder(@PathVariable String placeholder, @PathVariable String project) {
        return placeholderService.getRawByPlaceholder(placeholder, project);
    }
}
