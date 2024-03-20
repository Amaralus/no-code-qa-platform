package apps.amaralus.qa.platform.label.model.api;

import apps.amaralus.qa.platform.common.model.IdSource;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Label implements IdSource<Long> {
    private Long id;
    private @NotBlank String name;
    private String description;
    private @NotBlank String project;
}
