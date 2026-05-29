package apps.amaralus.qa.platform.runtime.action;

import apps.amaralus.qa.platform.runtime.execution.properties.StepExecutionProperties;

public class RestCallFactory implements ActionFactory {
    @Override
    public StepAction produceAction(StepExecutionProperties stepExecutionProperties) {
        return null;
    }

    @Override
    public ActionType factoryActionType() {
        return ActionType.REST;
    }
}
