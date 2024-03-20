package apps.amaralus.qa.platform.project.database;

import apps.amaralus.qa.platform.common.model.CrudModel;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("project")
public class ProjectModel implements CrudModel<String>, DatasetSource {
    @Id
    private String id;
    private String name;
    private String description;
    private long rootFolder;
    private long dataset;

}
