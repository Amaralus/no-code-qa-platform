package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.folder.model.FolderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderModel createProjectRoot(String projectName) {
        var root = new FolderModel("root", projectName);
        return folderRepository.save(root);
    }

    public void deleteAllByProject(String project) {
        folderRepository.deleteAll(folderRepository.findAllByProject(project));
    }
}
