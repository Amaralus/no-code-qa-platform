package apps.amaralus.qa.platform.dataset;

import java.util.Map;

public record Dataset(
        long id,
        String name,
        String alias,
        String description,
        String project,
        Map<String, Object> variables
) {
}
