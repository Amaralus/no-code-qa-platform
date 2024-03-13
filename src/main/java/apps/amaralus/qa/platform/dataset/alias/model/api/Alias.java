package apps.amaralus.qa.platform.dataset.alias.model.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Alias(
        long id,
        @NotBlank String name,
        @NotNull Long dataset,
        @NotBlank String variable
) {

    public Alias(String name, Long dataset, String variable) {
        this(0, name, dataset, variable);
    }

    public Alias(String name, Long dataset) {
        this(0, name, dataset, null);
    }
}
