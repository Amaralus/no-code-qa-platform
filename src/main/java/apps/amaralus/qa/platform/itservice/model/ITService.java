package apps.amaralus.qa.platform.itservice.model;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import lombok.Data;

@Data
public class ITService implements IdSource<Long>, DatasetSource {
    private Long id = 0L;
    private String name;
    private String description;
    private long dataset;
}
