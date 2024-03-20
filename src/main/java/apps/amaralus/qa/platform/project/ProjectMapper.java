package apps.amaralus.qa.platform.project;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.project.api.Project;
import apps.amaralus.qa.platform.project.database.ProjectModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProjectMapper extends GenericMapper<Project, ProjectModel> {
}
