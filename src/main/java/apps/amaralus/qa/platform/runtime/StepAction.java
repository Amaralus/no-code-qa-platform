package apps.amaralus.qa.platform.runtime;

import apps.amaralus.qa.platform.runtime.result.ExecutionResult;

import java.util.function.Supplier;

@FunctionalInterface
public interface StepAction extends Supplier<ExecutionResult> {
}
