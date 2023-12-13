package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.runtime.StepExecutionProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ActionsService {

    private final Map<ActionType, ActionFactory> factories;

    public ActionsService(List<ActionFactory> factories) {
        this.factories = factories.stream()
                .collect(Collectors.toMap(
                        ActionFactory::factoryActionType,
                        Function.identity()));
    }

    public StepAction produceAction(StepExecutionProperties stepExecutionProperties) {
        if (stepExecutionProperties.getActionType() == ActionType.NONE)
            throw new UnsupportedOperationException("NONE action type is unsupported!");

        return factories.get(stepExecutionProperties.getActionType()).produceAction(stepExecutionProperties);
    }
}
