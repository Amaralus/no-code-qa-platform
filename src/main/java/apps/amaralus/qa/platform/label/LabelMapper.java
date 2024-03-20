package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.label.model.api.Label;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LabelMapper extends GenericMapper<Label, LabelModel> {
}
