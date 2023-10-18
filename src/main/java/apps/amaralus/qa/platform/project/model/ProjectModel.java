package apps.amaralus.qa.platform.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("project")
public record ProjectModel(
        @Id String name,
        String description,
        long rootFolder
) {
}
