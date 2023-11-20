package apps.amaralus.qa.platform.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String alias) {
        super(String.format("Entity with alias - %s already exists", alias));
    }
}