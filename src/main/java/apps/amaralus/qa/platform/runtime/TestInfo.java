package apps.amaralus.qa.platform.runtime;

public record TestInfo(long id, String name) {

    @Override
    public String toString() {
        return "\"" + name + "\"#" + id;
    }
}
