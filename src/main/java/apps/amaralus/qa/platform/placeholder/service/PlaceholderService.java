package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.service.DatasetService;
import apps.amaralus.qa.platform.placeholder.enums.BaseGenPlaceholder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceholderService {

    private final DatasetService datasetService;
    private final PlaceholderResolver placeholderResolver;
    private final static String SEPARATOR = ":";

    public Object getByPlaceholder(String placeholder, String project) {
        return BaseGenPlaceholder.getOptionalByPlaceholder(placeholder)
                .orElseGet(() -> findByPropertyName(placeholder, project));
    }

    private Object findByPropertyName(String placeholder, String project) {

        String[] split = placeholder.split(SEPARATOR);
        var alias = split[0];
        var propertyName = split[1];

        Dataset dataset = datasetService.getByPath(alias, project);
        return placeholderResolver.getPropertyValue(propertyName, dataset.variables());
    }
}
