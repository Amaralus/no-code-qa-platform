package apps.amaralus.qa.platform.environment.itservice.model;

import apps.amaralus.qa.platform.common.model.IdSource;
import lombok.Data;

@Data
public class ITService implements IdSource<Long> {
    private Long id = 0L;
    private String name;
    private String description;
}
