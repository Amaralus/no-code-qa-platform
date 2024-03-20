package apps.amaralus.qa.platform.common;

import apps.amaralus.qa.platform.common.model.CrudModel;
import apps.amaralus.qa.platform.common.model.IdSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class CrudService<E extends IdSource<I>, M extends CrudModel<I>, I> {

    protected static final String ID_NOT_NULL_MESSAGE = "id must not be null!";
    protected final Class<E> entityClass = getGenericType(0);
    protected final Class<M> modelClass = getGenericType(1);
    protected final Class<I> idClass = getGenericType(2);
    protected GenericMapper<E, M> mapper;
    protected KeyValueRepository<M, I> repository;

    public @NotNull E create(@NotNull E entity) {
        Assert.notNull(entity, entityClass + " must not be null!");

        if (repository.existsById(entity.getId()))
            throw new IllegalArgumentException(entityClass + " with id=" + entity.getId() + " already exists!");

        var model = mapper.toModel(entity);
        beforeCreate(model);
        initIdValue(model);
        model = repository.save(model);
        afterCreate(model);

        return mapper.toEntity(model);
    }

    protected void beforeCreate(M model) {
        // do nothing
    }

    protected void afterCreate(M model) {
        // do nothing
    }

    public void delete(@NotNull I id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        repository.deleteById(id);
    }

    public Optional<E> findById(@NotNull I id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        return repository.findById(id)
                .map(mapper::toEntity);
    }

    public List<E> findAll() {
        return repository.findAll().stream()
                .map(mapper::toEntity)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> getGenericType(int position) {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[position];
    }

    @SuppressWarnings("unchecked")
    private void initIdValue(M model) {
        Object id;
        if (Long.class == idClass)
            id = 0L;
        else if (Integer.class == idClass)
            id = 0;
        else
            id = model.getId();
        model.setId((I) id);
    }

    @Autowired
    public void setMapper(GenericMapper<E, M> mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setRepository(KeyValueRepository<M, I> repository) {
        this.repository = repository;
    }
}
