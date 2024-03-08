package apps.amaralus.qa.platform.common.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String path) {
        super(String.format("Entity with path - %s already exists", path));
    }
}