package apps.amaralus.qa.platform.label;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public void deleteAllByProject(String project) {
        labelRepository.deleteAll(labelRepository.findAllByProject(project));
    }
}
