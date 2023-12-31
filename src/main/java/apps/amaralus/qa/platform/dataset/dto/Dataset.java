package apps.amaralus.qa.platform.dataset.dto;

import java.util.Map;

public record Dataset(
        long id,
        String name,
        String path,
        String description,
        String project,
        Map<String, Object> variables
) {
}
