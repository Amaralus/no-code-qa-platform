package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.folder.model.Folder;
import apps.amaralus.qa.platform.folder.model.FolderModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FolderMapper extends GenericMapper<Folder, FolderModel> {
}
