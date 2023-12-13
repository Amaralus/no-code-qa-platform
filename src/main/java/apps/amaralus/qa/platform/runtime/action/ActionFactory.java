package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.runtime.StepExecutionProperties;

public interface ActionFactory {

    StepAction produceAction(StepExecutionProperties stepExecutionProperties);

    ActionType factoryActionType();
}
