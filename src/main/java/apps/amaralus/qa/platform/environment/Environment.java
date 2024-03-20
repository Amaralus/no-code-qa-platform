package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Environment implements IdSource<Long>, DatasetSource {
    private Long id = 0L;
    @NotBlank
    private String name;
    private String description;
    private long dataset;
}
