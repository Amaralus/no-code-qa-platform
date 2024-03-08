package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.folder.model.FolderModel;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderService extends ProjectLinked {

    private final FolderRepository folderRepository;

    public FolderModel createProjectRoot(String projectName) {
        var root = new FolderModel("root", projectName);
        return folderRepository.save(root);
    }

    @Override
    public void deleteAllByProject() {
        folderRepository.deleteAll(folderRepository.findAllByProject(projectContext.getProjectId()));
    }
}
