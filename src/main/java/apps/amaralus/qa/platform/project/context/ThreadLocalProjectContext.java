package apps.amaralus.qa.platform.project.context;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ThreadLocalProjectContext implements ProjectContext {

    private final ThreadLocal<String> threadLocalProjectId = new ThreadLocal<>();

    @Override
    public void setProjectId(@NotNull String projectId) {
        Assert.notNull(projectId, "Project id must not be null!");
        this.threadLocalProjectId.set(projectId);
    }

    @Override
    public String getProjectId() {
        var projectId = threadLocalProjectId.get();
        if (projectId == null)
            throw new UninitializedContextException();
        return projectId;
    }

    public void clear() {
        threadLocalProjectId.remove();
    }
}
