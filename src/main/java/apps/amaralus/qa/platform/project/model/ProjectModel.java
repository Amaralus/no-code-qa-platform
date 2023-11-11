package apps.amaralus.qa.platform.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("project")
public class ProjectModel {
    @Id
    private String id;
    private String name;
    private String description;
    private long rootFolder;
    private long dataset;

}
