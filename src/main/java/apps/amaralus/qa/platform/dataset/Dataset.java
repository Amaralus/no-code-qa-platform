package apps.amaralus.qa.platform.dataset;

import java.util.List;

public record Dataset(
        long id,
        String name,
        String alias,
        String description,
        String project,
        List<Variable> variables
) {
}
