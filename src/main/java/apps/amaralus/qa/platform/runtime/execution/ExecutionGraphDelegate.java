package apps.amaralus.qa.platform.runtime.execution;

public interface ExecutionGraphDelegate {

    void setExecutionGraph(ExecutionGraph executionGraph);

    void executionGraphFinishedCallback();
}
