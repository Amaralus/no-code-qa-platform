package apps.amaralus.qa.platform.service;

import jakarta.validation.constraints.NotBlank;

public record ITService(
        @NotBlank String name,
        String description,
        @NotBlank String project,
        long dataset
) {
}
