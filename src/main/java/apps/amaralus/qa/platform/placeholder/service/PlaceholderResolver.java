package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.dataset.dto.Alias;
import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.service.DatasetService;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.placeholder.enums.BaseGenPlaceholder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlaceholderResolver {

    private final DatasetService datasetService;
    private static final String SEPARATOR = ":";
    private static final String DATASET = "dataset";

    /**
     * Находим значение по плейсхолдеру, например если плейсхолдер folder#3:some, то
     * он разбивается на path - folder#3 и ключ плейсхолдера - some
     * <p>
     * Если плейсходер вида dataset#3:some, то из path берется просто id датасета и напрямую
     * обращается к нему
     *
     * @param placeholder - ключ плейсхолдера
     * @param project     - проект у которого надо взять плейсхолдер
     * @param resolve     - вернуть разрешенный плейсхолдер или вернуть в сыром виде
     * @return разрешенный или неразрешенный плейсхолдер
     */
    public String findByPlaceholder(String placeholder, String project, boolean resolve) {

        String[] split = placeholder.split(SEPARATOR);
        var path = split[0];
        var propertyName = split[1];

        Dataset dataset;

        if (path.contains(DATASET)) {
            long id = Long.parseLong(path.split("#")[1]);
            dataset = datasetService.getById(id)
                    .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, id));
        } else {
            dataset = datasetService.getByPath(path, project)
                    .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class));
        }

        return resolve ? getPropertyValue(propertyName, dataset.variables()) : getRawString(propertyName, dataset.variables());
    }

    /**
     * Достать значение плейсхолдера через заданный для алиас
     * @param alias - быстрый доступ к плейсхолдера
     * @param resolve - вернуть разрешенный плейсхолдер или вернуть в сыром виде
     * @return разрешенный или неразрешенный плейсхолдер
     */
    public String getPropertyValueByAlias(Alias alias, boolean resolve) {
        var dataset = datasetService.getById(alias.dataset())
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, alias.dataset()));
        return resolve ? getPropertyValue(alias.propertyName(), dataset.variables()) : getRawString(alias.propertyName(), dataset.variables());
    }

    private String getPropertyValue(String key, Map<String, Object> properties) {
        return this.getPropertyValue(key, properties, new CircularDefinitionProtector());
    }

    private String getPropertyValue(String key, Map<String, Object> properties, CircularDefinitionProtector circularDefinitionProtector) {

        if (circularDefinitionProtector.isPropertyAlreadyVisited(key)) {
            circularDefinitionProtector.throwCircularDefinitionException();
        }


        String rawValue = getRawString(key, properties);

        if (StringUtils.isEmpty(rawValue)) {
            return null;
        }

        ExpansionBuffer buffer = new ExpansionBuffer(rawValue);
        String newKey;

        while ((newKey = buffer.extractNextPropertyKey()) != null) {
            buffer.moveResolvedPartToNextProperty();
            String newValue = getPropertyValue(newKey, properties, circularDefinitionProtector.cloneWithAdditionalKey(key));
            if (newValue == null) {
                buffer.add("{{" + newKey + "}}");
            } else {
                buffer.add(newValue);
            }
        }

        return buffer.getFullyResolved();
    }

    private String getRawString(String key, Map<String, Object> properties) {
        Object o = ObjectUtils.defaultIfNull(
                properties.get(key),
                BaseGenPlaceholder.getValueByPlaceholder(key)
        );

        return Objects.nonNull(o) ? o.toString() : null;
    }
}
