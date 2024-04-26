package apps.amaralus.qa.platform.project.context;

import org.jetbrains.annotations.NotNull;

public interface ProjectContext {

    void setProjectId(@NotNull String projectId);

    String getProjectId();
}
