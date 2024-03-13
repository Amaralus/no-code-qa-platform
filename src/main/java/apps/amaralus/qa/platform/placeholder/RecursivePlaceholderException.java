package apps.amaralus.qa.platform.placeholder;

import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;

import java.util.List;

public class RecursivePlaceholderException extends RuntimeException {

    public RecursivePlaceholderException(
            List<PlaceholderResolver.DatasetVariable> stack,
            PlaceholderResolver.DatasetVariable invalidDatasetVariable) {
        super(buildMessage(stack, invalidDatasetVariable));
    }

    private static String buildMessage(List<PlaceholderResolver.DatasetVariable> stack,
                                       PlaceholderResolver.DatasetVariable invalidDatasetVariable) {
        var builder = new StringBuilder("Recursive placeholder sequence detected: ");

        for (int i = stack.size() - 1; i >= 0; i--)
            appendPlaceholder(builder, stack.get(i)).append("->");
        appendPlaceholder(builder, invalidDatasetVariable);

        return builder.toString();
    }

    private static StringBuilder appendPlaceholder(StringBuilder builder,
                                                   PlaceholderResolver.DatasetVariable datasetVariable) {
        return builder.append("dataset#").append(datasetVariable.getDatasetId())
                .append(":").append(datasetVariable.getVariable());
    }
}
