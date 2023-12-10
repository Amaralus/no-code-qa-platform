package apps.amaralus.qa.platform.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> clazz, Object id) {
        super(String.format("%s with id - %s not found", clazz.getSimpleName(), id));
    }

    public EntityNotFoundException(Class<?> clazz) {
        super(String.format("%s not found", clazz.getSimpleName()));
    }
}
