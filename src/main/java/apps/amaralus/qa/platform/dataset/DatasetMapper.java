package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.common.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DatasetMapper extends GenericMapper<Dataset, DatasetModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DatasetModel update(@MappingTarget DatasetModel datasetModel, Dataset dataset);
}
