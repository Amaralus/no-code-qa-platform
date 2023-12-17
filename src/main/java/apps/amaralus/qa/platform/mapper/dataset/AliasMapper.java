package apps.amaralus.qa.platform.mapper.dataset;

import apps.amaralus.qa.platform.dataset.dto.Alias;
import apps.amaralus.qa.platform.dataset.model.AliasModel;
import apps.amaralus.qa.platform.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AliasMapper extends GenericMapper<Alias, AliasModel> {

    @Override
    @Mapping(target = "key.id", source = "id")
    @Mapping(target = "key.project", source = "project")
    AliasModel mapToM(Alias alias);

    @Override
    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "project", source = "key.project")
    Alias mapToD(AliasModel aliasModel);
}
