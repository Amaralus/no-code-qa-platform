package apps.amaralus.qa.platform.project.context;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
@Slf4j
public class DefaultProjectContext implements ProjectContext {

    private String projectId = "";

    public DefaultProjectContext() {
        log.debug("Project context created");
    }

    public void setProjectId(@NotNull String projectId) {
        Assert.notNull(projectId, "Project id must not be null!");
        this.projectId = projectId;
        log.debug("installed projectId: {}", projectId);
    }

    @Override
    public String getProjectId() {
        log.debug("requested projectId: {}", projectId);
        return projectId;
    }
}
