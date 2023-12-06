package apps.amaralus.qa.platform.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(String.format("%s with id - %s not found", entityClass.getName(), id));
    }

    public EntityNotFoundException(Class<?> entityClass) {
        super(String.format("%s not found", entityClass.getName()));
    }
}
