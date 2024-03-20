package apps.amaralus.qa.platform.project.api;

import apps.amaralus.qa.platform.common.model.IdSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements IdSource<String> {

        @Pattern(regexp = "(^[a-zA-Z0-9-]+$)", message = "Must be in kebab-case")
        private String id;
    @NotBlank
    private String name;
    private String description;
    private long rootFolder;
    private long dataset;
}
