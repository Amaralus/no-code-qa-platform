package apps.amaralus.qa.platform.project.context;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class DefaultProjectContext implements ProjectContext {

    private String projectId = "";

    public void setProjectId(@NotNull String projectId) {
        Assert.notNull(projectId, "Project id must not be null!");
        this.projectId = projectId;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }
}
