package apps.amaralus.qa.platform.project.model.api;

import jakarta.validation.constraints.NotBlank;

public record Project(
        @NotBlank String name,
        String description
) {
}
