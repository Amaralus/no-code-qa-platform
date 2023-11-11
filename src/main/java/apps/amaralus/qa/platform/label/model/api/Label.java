package apps.amaralus.qa.platform.label.model.api;

import jakarta.validation.constraints.NotBlank;

public record Label(
        @NotBlank String name,
        String description,
        @NotBlank String project
) {
}
