package apps.amaralus.qa.platform.testcase;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface TestCaseRepository extends KeyValueRepository<TestCaseModel, Long> {

    List<TestCaseModel> findAllByProject(String project);
}
