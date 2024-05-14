package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.runtime.execution.context.TestContext;
import apps.amaralus.qa.platform.runtime.execution.properties.StepExecutionProperties;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionModel;
import apps.amaralus.qa.platform.testcase.action.asserts.AssertActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static apps.amaralus.qa.platform.runtime.action.ActionType.ASSERT;

@Component
@RequiredArgsConstructor
public class AssertActionFactory implements ActionFactory {

    private static final String DEFAULT_FAIL_MESSAGE = "expected: [%1$s] but was: [%2$s]";

    private final AssertActionRepository assertActionRepository;

    @Override
    public StepAction produceAction(StepExecutionProperties stepExecutionProperties) {
        if (stepExecutionProperties.getActionType() != factoryActionType())
            throw new IllegalArgumentException();

        var action = assertActionRepository.findById(stepExecutionProperties.getExecutionAction())
                .orElseThrow(() -> new EntityNotFoundException(AssertActionModel.class, stepExecutionProperties.getExecutionAction()));

        return switch (action.getAssertion()) {
            case ASSERT_EQUALS -> new AssertStepAction(action.getExpected(), action.getActual(), DEFAULT_FAIL_MESSAGE);
            case ASSERT_NOT_EQUALS -> new AssertStepAction(action.getExpected(), action.getActual(), "expected: not equal but was: [%2$s]", true);
            case ASSERT_TRUE -> new AssertStepAction(true, action.getActual(), DEFAULT_FAIL_MESSAGE);
            case ASSERT_FALSE -> new AssertStepAction(false, action.getActual(), DEFAULT_FAIL_MESSAGE);
            case ASSERT_NULL -> new AssertStepAction(null, action.getActual(), DEFAULT_FAIL_MESSAGE);
            case ASSERT_NOT_NULL -> new AssertStepAction(null, action.getActual(), "expected: not [null]", true);
        };
    }

    @Override
    public ActionType factoryActionType() {
        return ASSERT;
    }

    private record AssertStepAction(Object expected,
                                    Object actual,
                                    String failMessage,
                                    boolean invert)
            implements StepAction {

        public AssertStepAction(Object expected, Object actual, String failMessage) {
            this(expected, actual, failMessage, false);
        }

        @Override
        public void execute(TestContext testContext) {
            var resolvedExpected = resolveIfString(testContext, expected);
            var resolvedActual = resolveIfString(testContext, actual);

            if (invert == Objects.equals(resolvedExpected, resolvedActual))
                testContext.fail(String.format(failMessage, resolvedExpected, resolvedActual));
        }

        private Object resolveIfString(TestContext testContext, Object value) {
            return value instanceof String string
                    ? testContext.resolvePlaceholdersText(string)
                    : value;
        }
    }
}
