package apps.amaralus.qa.platform.runtime.report.mapper;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.runtime.report.TestReport;
import apps.amaralus.qa.platform.runtime.report.TestReportModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestReportMapper extends GenericMapper<TestReport, TestReportModel> {
}
