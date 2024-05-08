package apps.amaralus.qa.platform.environment.serviceapi.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MediaType {
    ALL("*", "*"),
    APPLICATION_JSON("application", "json");

    private final String type;
    private final String subType;

    public String value() {
        return type + "/" + subType;
    }
}
