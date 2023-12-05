package apps.amaralus.qa.platform.mapper.dataset;

import apps.amaralus.qa.platform.dataset.dto.Alias;
import apps.amaralus.qa.platform.dataset.model.AliasModel;
import apps.amaralus.qa.platform.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AliasMapper extends GenericMapper<Alias, AliasModel> {
}
