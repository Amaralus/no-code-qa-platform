package apps.amaralus.qa.platform.mapper.environment;

import apps.amaralus.qa.platform.environment.Environment;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import apps.amaralus.qa.platform.mapper.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnvironmentMapper extends GenericMapper<Environment, EnvironmentModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EnvironmentModel update(@MappingTarget EnvironmentModel environmentModel, Environment environment);
}
