package apps.amaralus.qa.platform.label.model;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.HashSet;
import java.util.Set;

@KeySpace("label")
@Data
public class LabelModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("label-id")
    private Long id;
    private String name;
    private String description;
    private String project;
    private Set<Long> linkedTestCases = new HashSet<>();
    private Set<Long> linkedFolders = new HashSet<>();

    public void linkTestCase(Long testCase) {
        linkedTestCases.add(testCase);
    }

    public void unlinkTesCase(Long testCase) {
        linkedTestCases.remove(testCase);
    }

    public void linkFolder(Long folder) {
        linkedFolders.add(folder);
    }

    public void unlinkFolder(Long folder) {
        linkedFolders.remove(folder);
    }
}
