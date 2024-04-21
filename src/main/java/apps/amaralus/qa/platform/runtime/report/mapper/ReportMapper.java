package apps.amaralus.qa.platform.runtime.report.mapper;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.runtime.report.TestReportModel;
import apps.amaralus.qa.platform.runtime.report.api.Report;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ReportMapper extends GenericMapper<Report, TestReportModel> {
}
