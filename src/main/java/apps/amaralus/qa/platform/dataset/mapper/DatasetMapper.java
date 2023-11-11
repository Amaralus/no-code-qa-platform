package apps.amaralus.qa.platform.dataset.mapper;

import apps.amaralus.qa.platform.dataset.Dataset;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DatasetMapper {

    DatasetModel toDatasetModel(Dataset dataset);

    Dataset toDataset(DatasetModel datasetModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DatasetModel update(@MappingTarget DatasetModel datasetModel, Dataset dataset);
}
