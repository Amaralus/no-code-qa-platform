package apps.amaralus.qa.platform.testcase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    public List<TestCaseModel> findAllByProject(String project) {
        return testCaseRepository.findAllByProject(project);
    }
}
