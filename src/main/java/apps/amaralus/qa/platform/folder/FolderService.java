package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.folder.model.Folder;
import apps.amaralus.qa.platform.folder.model.FolderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderService extends DatasetLinkedService<Folder, FolderModel, Long> {

    public Folder createProjectRoot(String projectName) {
        var root = new Folder("root", projectName);
        return create(root);
    }

    // todo предотвращение удаления root папки
}
