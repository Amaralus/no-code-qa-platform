package apps.amaralus.qa.platform.itservice;

import jakarta.validation.constraints.NotBlank;

public record ITService(
        @NotBlank String name,
        String description,
        @NotBlank String project,
        long dataset
) {
}
