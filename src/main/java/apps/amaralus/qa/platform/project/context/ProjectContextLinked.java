package apps.amaralus.qa.platform.project.context;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class ProjectContextLinked {

    protected ProjectContext projectContext;

    @Autowired
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }
}
