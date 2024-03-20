package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.project.context.InterceptProjectId;
import apps.amaralus.qa.platform.project.context.ProjectContextLinked;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static apps.amaralus.qa.platform.common.api.Routes.ALIASES;

@RestController
@RequestMapping(ALIASES)
@RequiredArgsConstructor
public class AliasController extends ProjectContextLinked {

    private final AliasService aliasService;

    @PostMapping
    @InterceptProjectId
    public Alias createAlias(@PathVariable String project, @RequestBody Alias alias) {
        return aliasService.create(alias);
    }
}

