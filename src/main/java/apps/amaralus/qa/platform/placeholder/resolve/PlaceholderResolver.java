package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType;
import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.RecursivePlaceholderException;
import apps.amaralus.qa.platform.placeholder.generate.GeneratedPlaceholderType;
import apps.amaralus.qa.platform.placeholder.generate.PlaceholderGeneratorsProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Optional;

import static apps.amaralus.qa.platform.placeholder.DefaultPlaceholderType.UNKNOWN;

@RequiredArgsConstructor
public class PlaceholderResolver {

    private final ResolvingContext resolvingContext;
    private final PlaceholderGeneratorsProvider generatorsProvider;

    public Object resolve(Placeholder placeholder) {
        return new RecursiveStackResolver().resolve(placeholder);
    }

    public String resolve(String text) {
        return new RecursiveStackResolver().resolve(text);
    }

    private class RecursiveStackResolver {
        final LinkedList<DatasetVariable> stack = new LinkedList<>();

        Object resolve(Placeholder placeholder) {
            if (placeholder.getPlaceholderType() instanceof GeneratedPlaceholderType generatedType)
                return resolveGenerated(generatedType);

            var datasetVariable = new DatasetVariable(placeholder.getVariable());
            var dataset = findDataset(placeholder, datasetVariable);

            if (stack.contains(datasetVariable))
                throw new RecursivePlaceholderException(stack, datasetVariable);
            else
                stack.push(datasetVariable);

            var value = dataset.map(model -> model.getVariable(datasetVariable.variable)).orElse(null);

            if (value instanceof String text)
                value = resolve(text);

            stack.pop();
            return value;
        }

        String resolve(String text) {
            var matcher = Placeholder.BRACES_PATTERN.matcher(text);
            var builder = new StringBuilder();

            while (matcher.find()) {
                var value = resolve(Placeholder.parse(matcher.group()));
                matcher.appendReplacement(builder, String.valueOf(value));
            }
            matcher.appendTail(builder);

            return builder.toString();
        }

        Object resolveGenerated(GeneratedPlaceholderType generatedType) {
            return generatorsProvider.getGenerator(generatedType).generateValue();
        }

        Optional<DatasetModel> findDataset(Placeholder placeholder, DatasetVariable datasetVariable) {
            Optional<DatasetModel> dataset;
            if (placeholder.getPlaceholderType() == UNKNOWN)
                dataset = resolvingContext.findAlias(placeholder.getLocation())
                        .flatMap(alias -> {
                            if (alias.isVariableAlias())
                                datasetVariable.variable = alias.getVariable();
                            return resolvingContext.findDataset(alias.getDataset());
                        });
            else
                dataset = resolvingContext.findDataset((DefaultPlaceholderType) placeholder.getPlaceholderType(), placeholder.getId());

            dataset.ifPresent(datasetModel -> datasetVariable.datasetId = datasetModel.getId());
            return dataset;
        }
    }

    @Getter
    @EqualsAndHashCode
    public static class DatasetVariable {
        Long datasetId;
        String variable;

        public DatasetVariable(String variable) {
            this.variable = variable;
        }
    }
}
