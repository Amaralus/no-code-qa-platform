package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.project.context.ProjectLinked;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService extends ProjectLinked {

    private final LabelRepository labelRepository;

    @Override
    public void deleteAllByProject() {
        labelRepository.deleteAll(labelRepository.findAllByProject(projectContext.getProjectId()));
    }
}
