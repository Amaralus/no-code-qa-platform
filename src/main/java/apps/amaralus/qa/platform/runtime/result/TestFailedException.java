package apps.amaralus.qa.platform.runtime.result;

public class TestFailedException extends RuntimeException {

    public TestFailedException(String message) {
        super(message);
    }
}
