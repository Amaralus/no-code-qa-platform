package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.result.TestFailedException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class TestContext {

    private ExecutionResult executionResult = ExecutionResult.success();

    public void fail(String message) {
        throw new TestFailedException(message);
    }

    public void setResultMessage(String message) {
        executionResult = ExecutionResult.success(message);
    }

    public void putToDataset(@NotNull String property, Object value) {
        // work in progress, api may change
    }

    public Object getFromDataset(@NotNull String property) {
        // work in progress, api may change
        return null;
    }
}
