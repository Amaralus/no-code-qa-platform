package apps.amaralus.qa.platform.placeholder.accessor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DataAccessorProvider {

    private final List<DataAccessor> dataAccessors;

    public DataAccessorProvider(List<DataAccessor> dataAccessors) {
        this.dataAccessors = dataAccessors;
    }

    public Object getVariableByPath(String path, Object variable) {
        if (path == null) return variable;

        return dataAccessors.stream()
                .map(dataAccessor -> Optional.ofNullable(dataAccessor.read(variable, path)))
                .findAny()
                .flatMap(Function.identity())
                .orElse(null);
    }
}
