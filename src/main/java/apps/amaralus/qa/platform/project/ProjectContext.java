package apps.amaralus.qa.platform.project;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
@Getter
public class ProjectContext {

    private String project = "";

    public void setProject(String project) {
        Assert.notNull(project, "Project id must not be null!");
        this.project = project;
    }
}
