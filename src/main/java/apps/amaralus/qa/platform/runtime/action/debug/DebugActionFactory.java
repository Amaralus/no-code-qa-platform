package apps.amaralus.qa.platform.runtime.action.debug;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.runtime.action.ActionFactory;
import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.action.StepAction;
import apps.amaralus.qa.platform.runtime.execution.StepExecutionProperties;
import apps.amaralus.qa.platform.runtime.execution.context.TestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebugActionFactory implements ActionFactory {

    private final DebugActionRepository debugActionRepository;

    @Override
    public StepAction produceAction(StepExecutionProperties stepExecutionProperties) {
        if (stepExecutionProperties.getActionType() != factoryActionType())
            throw new IllegalArgumentException();

        var action = debugActionRepository.findById(stepExecutionProperties.getExecutionAction())
                .orElseThrow(() -> new EntityNotFoundException(DebugAction.class, stepExecutionProperties.getExecutionAction()));

        return new DebugStepAction(action.getMessage());
    }

    @Override
    public ActionType factoryActionType() {
        return ActionType.DEBUG;
    }

    private record DebugStepAction(String message) implements StepAction {
        @Override
        public void execute(TestContext testContext) {
            testContext.setResultMessage(testContext.resolvePlaceholdersText(message));
        }
    }
}
