package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.result.TestFailedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class TestContext {

    private final TestInfo testPlanInfo;
    private final TestInfo testCaseInfo;
    private final TestInfo testStepInfo;
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
