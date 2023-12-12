package apps.amaralus.qa.platform.placeholder.service;

import apps.amaralus.qa.platform.exception.PropertyCircularDefinitionException;

import java.util.LinkedHashSet;
import java.util.Set;

public class CircularDefinitionProtector {

    private final Set<String> keysUsed;

    public CircularDefinitionProtector(Set<String> usedKeySet) {
        this.keysUsed = usedKeySet;
    }

    public CircularDefinitionProtector() {
        this(new LinkedHashSet<>());
    }

    /**
     * Проверка, если проперти с таким именем уже было вызвано
     *
     * @param key - ключ, который определяет проперти
     * @return true если проперти уже было посещено
     */
    public boolean isPropertyAlreadyVisited(String key) {
        return keysUsed.contains(key);
    }

    /**
     * Проверка, что свойство не содержит циклического повторение плейсхолдеров.
     * Пример:
     * <p>
     * some.key = ${some.property}
     * some.property = ${some.key}
     * <p>
     * Это циклическое повторение
     *
     * @param key The key.
     * @return {@link CircularDefinitionProtector}
     */
    public CircularDefinitionProtector cloneWithAdditionalKey(String key) {
        var keysUsedCopy = new LinkedHashSet<>(keysUsed);
        keysUsedCopy.add(key);
        return new CircularDefinitionProtector(keysUsedCopy);
    }

    /**
     * Строим исключение если есть циркулярная зависимость и печатаем всю замкнутую цепочку
     */
    public void throwCircularDefinitionException() {
        StringBuilder buffer = new StringBuilder("Circular property definition detected: \n");
        keysUsed.forEach(key -> buffer.append(key).append(" --> "));
        buffer.append(keysUsed.stream().findFirst());
        throw new PropertyCircularDefinitionException(buffer.toString());
    }
}
