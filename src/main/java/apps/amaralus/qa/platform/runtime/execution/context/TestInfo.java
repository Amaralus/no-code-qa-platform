package apps.amaralus.qa.platform.runtime.execution.context;

import java.time.ZonedDateTime;

public record TestInfo(long id,
                       String name,
                       String project,
                       ZonedDateTime startTime) {
    public TestInfo(long id, String name, String project) {
        this(id, name, project, ZonedDateTime.now());
    }

    public String getTestName() {
        return name + "#" + startTime;
    }

    @Override
    public String toString() {
        return "\"" + name + "\"#" + id;
    }
}
