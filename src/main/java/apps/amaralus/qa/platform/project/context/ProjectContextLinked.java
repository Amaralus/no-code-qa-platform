package apps.amaralus.qa.platform.project.context;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class ProjectContextLinked {

    protected DefaultProjectContext projectContext;

    @Autowired
    public void setProjectContext(DefaultProjectContext projectContext) {
        this.projectContext = projectContext;
    }
}
