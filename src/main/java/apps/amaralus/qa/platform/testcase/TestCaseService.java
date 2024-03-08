package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.project.context.ProjectLinked;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestCaseService extends ProjectLinked {

    private final TestCaseRepository testCaseRepository;

    public List<TestCaseModel> findAllByProject(String project) {
        return testCaseRepository.findAllByProject(project);
    }

    @Override
    public void deleteAllByProject() {
        testCaseRepository.deleteAll(testCaseRepository.findAllByProject(projectContext.getProjectId()));
    }
}
