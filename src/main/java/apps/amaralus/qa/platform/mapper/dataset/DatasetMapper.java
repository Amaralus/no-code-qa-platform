package apps.amaralus.qa.platform.mapper.dataset;

import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.mapper.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DatasetMapper extends GenericMapper<Dataset, DatasetModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DatasetModel update(@MappingTarget DatasetModel datasetModel, Dataset dataset);
}
