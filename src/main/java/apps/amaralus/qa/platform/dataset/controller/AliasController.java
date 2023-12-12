package apps.amaralus.qa.platform.dataset.controller;

import apps.amaralus.qa.platform.dataset.dto.Alias;
import apps.amaralus.qa.platform.dataset.service.AliasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aliases")
@RequiredArgsConstructor
public class AliasController {

    private final AliasService aliasService;

    @PostMapping
    public Alias createAlias(@RequestBody Alias alias) {
        return aliasService.save(alias);
    }

    @PatchMapping("/{project}/{aliasName}")
    public Alias updateAliasName(@PathVariable String aliasName, @PathVariable String project, @RequestParam String newAlias) {
        return aliasService.updateAliasName(newAlias, aliasName, project);
    }

    @DeleteMapping("/{project}/{aliasName}")
    public void deleteByAliasName(@PathVariable String project, @PathVariable String aliasName) {
        aliasService.deleteAliasByName(aliasName, project);
    }
}

