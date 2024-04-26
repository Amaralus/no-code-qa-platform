package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.testcase.model.TestCase;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import org.springframework.stereotype.Service;

@Service
public class TestCaseService extends DatasetLinkedService<TestCase, TestCaseModel, Long> {
}
