package apps.amaralus.qa.platform.placeholder.accessor;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class JsonAccessor implements DataAccessor {

    @Override
    public Object read(Object json, String path) {
        String jsonPath = "$." + path;

        try {
            return JsonPath.read(json.toString(), jsonPath);
        } catch (PathNotFoundException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
