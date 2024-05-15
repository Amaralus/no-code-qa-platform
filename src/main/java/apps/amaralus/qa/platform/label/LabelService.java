package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.folder.FolderService;
import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.label.model.api.Label;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import apps.amaralus.qa.platform.testcase.TestCaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LabelService extends ProjectLinkedService<Label, LabelModel, Long> {

    private FolderService folderService;
    private TestCaseService testCaseService;

    public void linkTestCase(@NotNull Long id, @NotNull Long testCase) {
        Assert.notNull(testCase, "testCase must not be null!");
        findModifySave(id, label -> label.linkTestCase(testCase));
    }

    public void unlinkTestCase(@NotNull Long id, @NotNull Long testCase) {
        Assert.notNull(testCase, "testCase must not be null!");
        findModifySave(id, label -> label.unlinkTesCase(testCase));
    }

    public void linkFolder(@NotNull Long id, @NotNull Long folder) {
        Assert.notNull(folder, "folder must not be null!");
        findModifySave(id, label -> label.linkFolder(folder));
    }

    public void unlinkFolder(@NotNull Long id, @NotNull Long folder) {
        Assert.notNull(folder, "folder must not be null!");
        findModifySave(id, label -> label.unlinkFolder(folder));
    }

    @Override
    public void delete(@NotNull Long id) {
        findModelById(id).ifPresent(label -> {
            label.getLinkedTestCases().forEach(testCase -> testCaseService.removeLabel(testCase, id));
            label.getLinkedFolders().forEach(folder -> folderService.removeLabel(folder, id));
        });
        super.delete(id);
    }

    @Autowired
    public void setTestCaseService(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @Autowired
    public void setFolderService(FolderService folderService) {
        this.folderService = folderService;
    }
}
