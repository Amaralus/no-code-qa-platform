package apps.amaralus.qa.platform.folder.model;

import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.label.linked.LabelLinkedModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.HashSet;
import java.util.Set;

@KeySpace("folder")
@Data
public class FolderModel implements ProjectLinkedModel<Long>, DatasetSource, LabelLinkedModel {
    @Id
    @GeneratedSequence("folder-id")
    private Long id;
    private String name;
    private String description;
    private Set<Long> labels = new HashSet<>();
    private Long parent;
    private long dataset;
    private String project;

    @Override
    public void addLabel(Long label) {
        labels.add(label);
    }

    @Override
    public void removeLabel(Long label) {
        labels.remove(label);
    }
}
