package apps.amaralus.qa.platform.dataset.alias;

import apps.amaralus.qa.platform.dataset.alias.model.api.Alias;
import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.common.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AliasMapper extends GenericMapper<Alias, AliasModel> {
}
