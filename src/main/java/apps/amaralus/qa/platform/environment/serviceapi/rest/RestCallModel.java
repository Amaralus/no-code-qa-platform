package apps.amaralus.qa.platform.environment.serviceapi.rest;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RestCallModel {
    private String name;
    private boolean https;
    private Method method;
    private String path;
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private MediaType contentType;
}
