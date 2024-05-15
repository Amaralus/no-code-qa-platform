package apps.amaralus.qa.platform.testcase;

import apps.amaralus.qa.platform.dataset.linked.DatasetLinkedService;
import apps.amaralus.qa.platform.label.LabelService;
import apps.amaralus.qa.platform.testcase.model.TestCase;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import apps.amaralus.qa.platform.testcase.model.TestCaseStatus;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class TestCaseService extends DatasetLinkedService<TestCase, TestCaseModel, Long> {

    private LabelService labelService;

    public @NotNull TestCase updateName(@NotNull Long id, @NotNull String name) {
        Assert.notNull(name, "name must not be null!");
        return findModifySave(id, testCase -> testCase.setName(name));
    }

    public @NotNull TestCase updateDescription(@NotNull Long id, @Nullable String description) {
        return findModifySave(id, testCase -> testCase.setDescription(description));
    }

    public @NotNull TestCase updateStatus(@NotNull Long id, @NotNull TestCaseStatus status) {
        Assert.notNull(status, "status must not be null!");
        return findModifySave(id, testCase -> testCase.setStatus(status));
    }

    public void addLabel(@NotNull Long id, @NotNull Long label) {
        Assert.notNull(label, "label must not be null!");
        findModifySave(id, testCase -> testCase.addLabel(label));
        labelService.linkTestCase(label, id);
    }

    public void removeLabel(@NotNull Long id, @NotNull Long label) {
        Assert.notNull(label, "label must not be null!");
        findModifySave(id, testCase -> testCase.removeLabel(label));
        labelService.unlinkTestCase(label, id);
    }

    @Autowired
    @Lazy
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }
}
