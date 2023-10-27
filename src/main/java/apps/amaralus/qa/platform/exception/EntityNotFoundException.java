package apps.amaralus.qa.platform.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String className, String id) {
        super(String.format("%s with id - %s not found", className, id));
    }
}