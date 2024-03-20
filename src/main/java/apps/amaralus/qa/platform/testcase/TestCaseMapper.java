package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.common.GenericMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestCaseMapper extends GenericMapper<TestCase, TestCaseModel> {
}
