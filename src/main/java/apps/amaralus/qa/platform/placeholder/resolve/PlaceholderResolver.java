package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.generate.GeneratedPlaceholderType;
import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceholderResolver {

    private final ResolvingContext resolvingContext;
    private final PlaceholderGeneratorsProvider generatorsProvider;

    public Object resolve(Placeholder placeholder, String project) {
        var placeholderType = placeholder.getPlaceholderType();
        if (placeholderType instanceof GeneratedPlaceholderType generatedType)
            return generatorsProvider.getGenerator(generatedType).generateValue();

        return resolvingContext.getDatasetById(placeholderType, placeholder.getId(), project)
                .map(datasetModel -> datasetModel.getVariables().get(placeholder.getVariable()))
                .orElse(null);
    }
}
