package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.dataset.service.AliasService;
import apps.amaralus.qa.platform.placeholder.enums.BaseGenPlaceholder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlaceholderService {

    private final AliasService aliasService;
    private final PlaceholderResolver placeholderResolver;

    public Object getResolveByPlaceholder(String placeholder, String project) {
        return getByPlaceholder(placeholder, project, true);
    }

    public Object getRawByPlaceholder(String placeholder, String project) {
        return getByPlaceholder(placeholder, project, false);
    }

    private Object getByPlaceholder(String placeholder, String project, boolean resolve) {
        Object value = BaseGenPlaceholder.getOptionalByPlaceholder(placeholder)
                .orElseGet(() -> findByAlias(placeholder, project, resolve));
        return Objects.isNull(value) ? findByPropertyName(placeholder, project, resolve) : value;
    }

    private Object findByAlias(String placeholder, String project, boolean resolve) {

        var alias = aliasService.getAliasByName(placeholder, project);

        if (Objects.isNull(alias)) return null;

        return placeholderResolver.getPropertyValueByAlias(alias, resolve);
    }

    private Object findByPropertyName(String placeholder, String project, boolean resolve) {
        return placeholderResolver.findByPlaceholder(placeholder, project, resolve);
    }
}
