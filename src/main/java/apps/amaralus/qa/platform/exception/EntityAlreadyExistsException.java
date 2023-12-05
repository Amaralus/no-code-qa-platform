package apps.amaralus.qa.platform.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String path) {
        super(String.format("Entity with path - %s already exists", path));
    }
}