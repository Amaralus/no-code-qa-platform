package apps.amaralus.qa.platform.project;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("project")
public record Project(
        @Id String name,
        long rootFolder,
        String description
) {
}
