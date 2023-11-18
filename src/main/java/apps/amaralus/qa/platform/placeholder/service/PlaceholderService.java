package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.placeholder.enums.Placeholder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceholderService {

    private final DatasetService datasetService;

    private final static String SEPARATOR = ";";

    public Object getByPlaceholder(String placeholder, String project) {
        return Placeholder.getValueByPlaceholder(placeholder)
                .orElseGet(() -> findByPropertyName(placeholder, project));
    }

    private Object findByPropertyName(String placeholder, String project) {

        String[] split = placeholder.split(SEPARATOR);
        var alias = split[0];
        var propertyName = split[1];

        return datasetService.getByAlias(alias, project)
                .variables()
                .get(propertyName);
    }
}
