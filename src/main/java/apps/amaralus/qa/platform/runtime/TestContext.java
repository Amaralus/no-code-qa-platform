package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.result.DefaultResult;
import apps.amaralus.qa.platform.runtime.result.ExecutionResult;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class TestContext {

    private ExecutionResult executionResult = new DefaultResult(false);

    public void fail(String message) {
        executionResult = new DefaultResult(message == null ? "" : message, true);
    }

    public void putToDataset(@NotNull String property, Object value) {
        // work in progress, api may change
    }

    public Object getFromDataset(@NotNull String property) {
        // work in progress, api may change
        return null;
    }
}
