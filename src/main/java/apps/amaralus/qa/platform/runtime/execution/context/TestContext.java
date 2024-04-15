package apps.amaralus.qa.platform.runtime.execution.context;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.placeholder.InvalidPlaceholderException;
import apps.amaralus.qa.platform.placeholder.Placeholder;
import apps.amaralus.qa.platform.placeholder.resolve.PlaceholderResolver;
import apps.amaralus.qa.platform.runtime.execution.result.ExecutionResult;
import apps.amaralus.qa.platform.runtime.execution.result.TestFailedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.Assert;

@Getter
@RequiredArgsConstructor
public class TestContext {

    private final TestInfo testPlanInfo;
    private final TestInfo testCaseInfo;
    private final TestInfo testStepInfo;
    private final PlaceholderResolver placeholderResolver;
    private final DatasetModel testCaseDataset;
    private ExecutionResult executionResult = ExecutionResult.success();

    /**
     * Interrupt test step execution
     *
     * @param message Error message
     * @throws TestFailedException interrupt execution exception
     */
    public void fail(String message) {
        throw new TestFailedException(message);
    }

    public void setResultMessage(String message) {
        executionResult = ExecutionResult.success(message);
    }

    public void putToDataset(@NotNull String property, @Nullable Object value) {
        Assert.notNull(property, "property name must not be null!");
        testCaseDataset.setVariable(property, value);
    }

    public @Nullable Object getFromDataset(@NotNull String property) {
        Assert.notNull(property, "property name must not be null!");
        return testCaseDataset.getVariable(property);
    }

    public Object resolvePlaceholdersText(String text) {
        try {
            return placeholderResolver.resolve(Placeholder.parse(text));
        } catch (InvalidPlaceholderException ignore) {
            return placeholderResolver.resolve(text);
        }
    }
}
