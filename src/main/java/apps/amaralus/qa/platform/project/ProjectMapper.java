package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.project.model.ProjectModel;
import apps.amaralus.qa.platform.project.model.api.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper extends GenericMapper<Project, ProjectModel> {
}
