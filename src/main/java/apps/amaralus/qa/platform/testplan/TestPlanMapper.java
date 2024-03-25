package apps.amaralus.qa.platform.testplan;

import apps.amaralus.qa.platform.common.GenericMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestPlanMapper extends GenericMapper<TestPlan, TestPlanModel> {
}
