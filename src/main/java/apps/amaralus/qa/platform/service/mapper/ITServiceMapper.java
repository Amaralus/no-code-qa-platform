package apps.amaralus.qa.platform.service.mapper;

import apps.amaralus.qa.platform.service.ITService;
import apps.amaralus.qa.platform.service.model.ITServiceModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITServiceMapper {

    ITServiceModel toITServiceModel(ITService itService);

    ITService toITService(ITServiceModel itServiceModel);

    List<ITService> toITServices(List<ITServiceModel> itServiceModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ITServiceModel update(@MappingTarget ITServiceModel serviceModel, ITService itService);
}
