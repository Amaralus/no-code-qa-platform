package apps.amaralus.qa.platform.runtime.execution.context;

public record TestInfo(long id,
                       String name,
                       String project) {

    @Override
    public String toString() {
        return "\"" + name + "\"#" + id;
    }
}
