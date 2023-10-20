package apps.amaralus.qa.platform.testcase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    public void deleteAllByProject(String project) {
        testCaseRepository.deleteAll(testCaseRepository.findAllByProject(project));
    }
}
