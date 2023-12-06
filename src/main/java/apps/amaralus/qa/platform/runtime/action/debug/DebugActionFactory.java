package apps.amaralus.qa.platform.runtime.action.debug;

import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.runtime.StepExecutionProperties;
import apps.amaralus.qa.platform.runtime.TestContext;
import apps.amaralus.qa.platform.runtime.action.ActionFactory;
import apps.amaralus.qa.platform.runtime.action.ActionType;
import apps.amaralus.qa.platform.runtime.action.StepAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// todo add Placeholder resolver
public class DebugActionFactory implements ActionFactory {

    private final DebugActionRepository debugActionRepository;

    @Override
    public StepAction produceAction(StepExecutionProperties stepExecutionProperties) {
        if (stepExecutionProperties.getActionType() != factoryActionType())
            throw new IllegalArgumentException();

        var action = debugActionRepository.findById(stepExecutionProperties.getExecutionAction())
                .orElseThrow(() -> new EntityNotFoundException(DebugAction.class, stepExecutionProperties.getExecutionAction()));

        // todo resolve message to possible placeholders
        return new DebugStepAction(action.getMessage());
    }

    @Override
    public ActionType factoryActionType() {
        return ActionType.DEBUG;
    }

    @Slf4j
    private record DebugStepAction(String message) implements StepAction {
        @Override
        public void execute(TestContext testContext) {
            log.debug("Debug step message: {}", message);
            // todo put message to result
        }
    }
}
