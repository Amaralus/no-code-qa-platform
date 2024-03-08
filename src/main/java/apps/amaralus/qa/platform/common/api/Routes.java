package apps.amaralus.qa.platform.common.api;

public final class Routes {

    public static final String API = "/api";
    public static final String PROJECTS = API + "/projects";
    public static final String PROJECTS_VARIABLE = API + "/projects/{project}";
    public static final String DATASETS = PROJECTS_VARIABLE + "/datasets";

    private Routes() {
    }
}
