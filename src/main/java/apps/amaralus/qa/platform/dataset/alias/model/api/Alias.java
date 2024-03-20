package apps.amaralus.qa.platform.dataset.alias.model.api;

import apps.amaralus.qa.platform.common.model.IdSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alias implements IdSource<Long> {

    private Long id = 0L;
    @Pattern(regexp = "(^[a-zA-Z0-9-]+$)", message = "Must be in kebab-case")
    private String name;
    private String description;
    @NotNull
    private Long entityId;
    @NotBlank
    private String variable;

    public Alias(String name, String description, Long entityId, String variable) {
        this(0L, name, description, entityId, variable);
    }

    public Alias(String name, String description, Long entityId) {
        this(0L, name, description, entityId, null);
    }
}
