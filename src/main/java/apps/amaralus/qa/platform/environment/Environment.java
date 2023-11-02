package apps.amaralus.qa.platform.environment;

import jakarta.validation.constraints.NotBlank;

public record Environment(
        @NotBlank String name,
        String description,
        @NotBlank String project
) {
}
