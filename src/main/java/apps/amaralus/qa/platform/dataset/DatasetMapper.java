package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DatasetMapper extends GenericMapper<Dataset, DatasetModel> {
}
