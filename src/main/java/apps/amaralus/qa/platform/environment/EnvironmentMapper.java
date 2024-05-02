package apps.amaralus.qa.platform.environment;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.environment.api.Environment;
import apps.amaralus.qa.platform.environment.database.EnvironmentModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnvironmentMapper extends GenericMapper<Environment, EnvironmentModel> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EnvironmentModel update(@MappingTarget EnvironmentModel environmentModel, Environment environment);
}
