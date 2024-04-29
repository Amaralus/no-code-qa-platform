package apps.amaralus.qa.platform.testplan.report.mapper;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.runtime.execution.context.TestInfo;
import apps.amaralus.qa.platform.testplan.report.TestReport;
import apps.amaralus.qa.platform.testplan.report.model.TestReportModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestReportMapper extends GenericMapper<TestReport, TestReportModel> {

    @AfterMapping
    default void enrich(@MappingTarget TestReportModel model, TestReport report) {
        TestInfo testInfo = report.getTestInfo();
        model.setTestPlanId(testInfo.id());
        model.setName(testInfo.name() + "#" + model.getStartTime());
    }
}
