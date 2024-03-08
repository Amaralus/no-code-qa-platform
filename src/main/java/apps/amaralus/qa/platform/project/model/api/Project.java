package apps.amaralus.qa.platform.project.model.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Project(
        @Pattern(regexp = "(^[a-zA-Z0-9-]+$)", message = "Must be in kebab-case")
        String id,
        @NotBlank String name,
        String description,
        long dataset
) {
}
