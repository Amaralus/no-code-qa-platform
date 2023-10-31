package apps.amaralus.qa.platform.dataset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;

    public void deleteAllByProject(String project) {
        datasetRepository.deleteAllByProject(project);
    }
}
