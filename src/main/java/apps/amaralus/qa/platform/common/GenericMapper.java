package apps.amaralus.qa.platform.common;

import java.util.List;

public interface GenericMapper<E, M> {
    E toEntity(M model);

    M toModel(E entity);

    List<M> toModelList(List<E> entityList);

    List<E> toEntityList(List<M> modelList);
}
