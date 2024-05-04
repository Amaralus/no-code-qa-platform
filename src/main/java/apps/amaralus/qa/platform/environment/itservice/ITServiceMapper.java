package apps.amaralus.qa.platform.environment.itservice;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.environment.itservice.model.ITService;
import apps.amaralus.qa.platform.environment.itservice.model.ITServiceModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ITServiceMapper extends GenericMapper<ITService, ITServiceModel> {
}
