package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.common.GenericMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestReportMapper extends GenericMapper<TestReport, TestReportModel> {
    @AfterMapping
    default void enrich(@MappingTarget TestReportModel model, TestReport entity) {
        model.setReport(entity.toString());
    }
}
