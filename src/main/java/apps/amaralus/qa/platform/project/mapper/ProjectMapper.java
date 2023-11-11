package apps.amaralus.qa.platform.project.mapper;

import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toProject(ProjectModel projectModel);

    ProjectModel toProjectModel(Project project);
}
