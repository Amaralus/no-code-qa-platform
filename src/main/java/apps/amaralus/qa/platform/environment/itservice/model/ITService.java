package apps.amaralus.qa.platform.environment.itservice.model;

import apps.amaralus.qa.platform.common.model.IdSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ITService implements IdSource<Long> {
    private Long id = 0L;
    private String name;
    private String description;
    private String host;
    private Integer port;
}
