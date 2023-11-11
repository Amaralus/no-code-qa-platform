package apps.amaralus.qa.platform.mapper.service;

import apps.amaralus.qa.platform.mapper.GenericMapper;
import apps.amaralus.qa.platform.service.ITService;
import apps.amaralus.qa.platform.service.model.ITServiceModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ITServiceMapper extends GenericMapper<ITService, ITServiceModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ITServiceModel update(@MappingTarget ITServiceModel serviceModel, ITService itService);
}
