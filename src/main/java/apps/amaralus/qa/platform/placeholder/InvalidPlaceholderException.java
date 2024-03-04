package apps.amaralus.qa.platform.placeholder;

public class InvalidPlaceholderException extends RuntimeException {

    public InvalidPlaceholderException(String placeholderString) {
        super(String.format("Invalid placeholder \"%s\"", placeholderString));
    }
}
