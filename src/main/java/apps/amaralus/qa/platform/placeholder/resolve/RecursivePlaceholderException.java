package apps.amaralus.qa.platform.placeholder.resolve;

import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver.DatasetVariable;

import java.util.List;

public class RecursivePlaceholderException extends RuntimeException {

    public RecursivePlaceholderException(
            List<DatasetVariable> stack,
            DatasetVariable invalidDatasetVariable) {
        super(buildMessage(stack, invalidDatasetVariable));
    }

    private static String buildMessage(List<DatasetVariable> stack,
                                       DatasetVariable invalidDatasetVariable) {
        var builder = new StringBuilder("Recursive placeholder sequence detected: ");

        for (int i = stack.size() - 1; i >= 0; i--)
            appendPlaceholder(builder, stack.get(i), stack.get(i).equals(invalidDatasetVariable))
                    .append("->");
        appendPlaceholder(builder, invalidDatasetVariable, true);

        return builder.toString();
    }

    private static StringBuilder appendPlaceholder(StringBuilder builder,
                                                   DatasetVariable datasetVariable,
                                                   boolean appendBrackets) {
        if (appendBrackets)
            builder.append("[");
        builder.append("dataset#").append(datasetVariable.getDatasetId())
                .append(":").append(datasetVariable.getVariable());
        if (appendBrackets)
            builder.append("]");
        return builder;
    }
}
