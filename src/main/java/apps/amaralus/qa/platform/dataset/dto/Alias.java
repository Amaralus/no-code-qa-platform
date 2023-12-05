package apps.amaralus.qa.platform.dataset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Alias(
        long id,
        @NotBlank String name,
        @NotNull Long dataset,
        @NotBlank String propertyName,
        @NotBlank String project
) {
}
