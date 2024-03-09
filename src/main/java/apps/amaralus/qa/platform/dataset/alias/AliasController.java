package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static apps.amaralus.qa.platform.common.api.Routes.ALIASES;

@RestController
@RequestMapping(ALIASES)
@RequiredArgsConstructor
public class AliasController {

    private final AliasService aliasService;

    @PostMapping
    @InterceptProjectId
    public Alias createAlias(@PathVariable String project, @RequestBody Alias alias) {
        return aliasService.save(alias);
    }

    @PatchMapping("/{aliasName}")
    @InterceptProjectId
    public Alias updateAliasName(@PathVariable String aliasName, @PathVariable String project, @RequestParam String newAlias) {
        return aliasService.updateAliasName(newAlias, aliasName, project);
    }

    @DeleteMapping("/{aliasName}")
    @InterceptProjectId
    public void deleteByAliasName(@PathVariable String project, @PathVariable String aliasName) {
        aliasService.deleteAliasByName(aliasName, project);
    }
}

