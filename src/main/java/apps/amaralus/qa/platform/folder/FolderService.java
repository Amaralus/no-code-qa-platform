package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.folder.model.Folder;
import apps.amaralus.qa.platform.folder.model.FolderModel;
import apps.amaralus.qa.platform.label.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
// todo предотвращение удаления родительской папки
public class FolderService extends DatasetLinkedService<Folder, FolderModel, Long> {

    private LabelService labelService;

    public Folder createProjectRoot(String projectName) {
        var root = new Folder("root", projectName);
        return create(root);
    }

    public void addLabel(@NotNull Long id, @NotNull Long label) {
        Assert.notNull(label, "label must not be null!");
        findModifySave(id, folder -> folder.addLabel(label));
        labelService.linkFolder(label, id);
    }

    public void removeLabel(@NotNull Long id, @NotNull Long label) {
        Assert.notNull(label, "label must not be null!");
        findModifySave(id, folder -> folder.removeLabel(label));
        labelService.unlinkFolder(label, id);
    }

    @Autowired
    @Lazy
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }
}
