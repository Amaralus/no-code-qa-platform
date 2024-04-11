package apps.amaralus.qa.platform.dataset.linked;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.model.Backlink;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public abstract class DatasetLinkedService<
        E extends IdSource<I> & DatasetSource,
        M extends ProjectLinkedModel<I> & DatasetSource,
        I>
        extends ProjectLinkedService<E, M, I> {

    protected DatasetService datasetService;

    @Override
    protected void afterCreate(M model) {
        var dataset = datasetService.create(
                new Dataset(createDatasetName(model.getId()), null, true, new Backlink<>(modelClass, model.getId())));
        model.setDataset(dataset.getId());
    }

    private String createDatasetName(I id) {
        return "System dataset for entity " + entityClass.getName().toLowerCase() + "#" + id;
    }

    @Override
    public void delete(@NotNull I id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        findById(id)
                .map(DatasetSource::getDataset)
                .ifPresent(datasetService::delete);
        super.delete(id);
    }

    @Autowired
    public void setDatasetService(DatasetService datasetService) {
        this.datasetService = datasetService;
    }
}
