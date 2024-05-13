package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.common.GenericMapper;
import apps.amaralus.qa.platform.testcase.model.TestCase;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestCaseMapper extends GenericMapper<TestCase, TestCaseModel> {
}
