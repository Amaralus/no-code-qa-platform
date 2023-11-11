package apps.amaralus.qa.platform.environment;

import jakarta.validation.constraints.NotBlank;

public record Environment(
        long id,
        @NotBlank String name,
        String description,
        @NotBlank String project,
        long dataset
) {
}
