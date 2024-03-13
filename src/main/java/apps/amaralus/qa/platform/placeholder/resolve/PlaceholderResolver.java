package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.generate.GeneratedPlaceholderType;
import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

import static apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType.UNKNOWN;

@RequiredArgsConstructor
public class PlaceholderResolver {

    private final ResolvingContext resolvingContext;
    private final PlaceholderGeneratorsProvider generatorsProvider;

    public Object resolve(Placeholder placeholder) {
        if (placeholder.getPlaceholderType() instanceof GeneratedPlaceholderType generatedType)
            return resolveGenerated(generatedType);

        if (placeholder.getPlaceholderType() == UNKNOWN)
            return resolveAlias(placeholder);

        return resolveDefault(placeholder);

    }

    private Object resolveDefault(Placeholder placeholder) {
        return resolvingContext.findDataset((DefaultPlaceholderType) placeholder.getPlaceholderType(), placeholder.getId())
                .map(datasetModel -> datasetModel.getVariable(placeholder.getVariable()))
                .orElse(null);
    }

    private Object resolveGenerated(GeneratedPlaceholderType generatedType) {
        return generatorsProvider.getGenerator(generatedType).generateValue();
    }

    private Object resolveAlias(Placeholder placeholder) {
        return resolvingContext.findAlias(placeholder.getLocation())
                .flatMap(alias -> resolvingContext.findDataset(alias.getDataset())
                        .map(model -> model.getVariable(
                                alias.isDatasetAlias()
                                        ? placeholder.getVariable()
                                        : alias.getVariable())))
                .orElse(null);
    }

    // todo in progress
    @RequiredArgsConstructor
    private static class StackResolver {
        final LinkedList<?> stack = new LinkedList<>();
        final PlaceholderResolver resolver;
    }
}
