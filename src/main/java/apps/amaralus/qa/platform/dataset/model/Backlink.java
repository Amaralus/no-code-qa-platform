package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.common.model.CrudModel;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;

public record Backlink<M extends CrudModel<I> & DatasetSource, I>
        (Class<M> modelCLass, I id, Class<?> idType) {

    public Backlink(Class<M> modelCLass, I id) {
        this(modelCLass, id, id.getClass());
    }

    @SuppressWarnings("unchecked")
    public Backlink(Class<M> modelCLass, I id, Class<?> idType) {
        this.modelCLass = modelCLass;
        this.idType = idType;

        // из-за того что в бд хранится json Long значение может мигрировать в Integer
        if (id instanceof Integer integerId && idType == Long.class)
            this.id = (I) Long.valueOf(integerId.longValue());
        else
            this.id = id;
    }
}
