package apps.amaralus.qa.platform.common.api;

public final class Routes {

    public static final String API = "/api";
    public static final String PROJECTS = API + "/projects";
    public static final String PROJECTS_VARIABLE = PROJECTS + "/{project}";
    public static final String DATASETS = PROJECTS_VARIABLE + "/datasets";
    public static final String ENVIRONMENTS = PROJECTS_VARIABLE + "/environments";
    public static final String IT_SERVICES = PROJECTS_VARIABLE + "/it-services";
    public static final String ALIASES = PROJECTS_VARIABLE + "/aliases";
    public static final String REPORTS = PROJECTS_VARIABLE + "/reports";

    private Routes() {
    }
}
