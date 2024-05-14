package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.runtime.execution.context.TestContext;
import apps.amaralus.qa.platform.runtime.execution.properties.StepExecutionProperties;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionModel;
import apps.amaralus.qa.platform.testcase.action.debug.DebugActionRepository;
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
                .orElseThrow(() -> new EntityNotFoundException(DebugActionModel.class, stepExecutionProperties.getExecutionAction()));

        return new DebugStepAction(action.getMessage());
    }

    @Override
    public ActionType factoryActionType() {
        return ActionType.DEBUG;
    }

    private record DebugStepAction(String message) implements StepAction {
        @Override
        public void execute(TestContext testContext) {
            testContext.setResultMessage(String.valueOf(testContext.resolvePlaceholdersText(message)));
        }
    }
}
