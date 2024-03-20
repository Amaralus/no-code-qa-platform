package apps.amaralus.qa.platform.common.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(Object id) {
        super(String.format("Entity with id - %s already exists", id));
    }
}