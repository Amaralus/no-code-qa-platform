package apps.amaralus.qa.platform.itservice;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.itservice.model.ITServiceModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ITServiceMapper extends GenericMapper<ITService, ITServiceModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ITServiceModel update(@MappingTarget ITServiceModel serviceModel, ITService itService);
}
