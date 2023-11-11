package apps.amaralus.qa.platform.environment.mapper;

import apps.amaralus.qa.platform.environment.Environment;
import apps.amaralus.qa.platform.environment.model.EnvironmentModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnvironmentMapper {

    EnvironmentModel toEnvironmentModel(Environment environment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EnvironmentModel update(@MappingTarget EnvironmentModel environmentModel, Environment environment);

    Environment toEnvironment(EnvironmentModel environmentModel);

    List<Environment> toEnvironments(List<EnvironmentModel> environmentModels);
}
