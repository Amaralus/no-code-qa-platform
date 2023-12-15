package apps.amaralus.qa.platform.runtime.execution;

// todo придумать название получше
public interface ExecutionGraphDelegate {

    void setExecutionGraph(ExecutionGraph executionGraph);

    void graphExecutionFinishedCallback();
}
