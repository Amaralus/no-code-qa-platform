package apps.amaralus.qa.platform.runtime.execution.result;

public class TestFailedException extends RuntimeException {

    public TestFailedException(String message) {
        super(message);
    }
}
