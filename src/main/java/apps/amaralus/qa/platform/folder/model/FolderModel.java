package apps.amaralus.qa.platform.folder.model;

import apps.amaralus.qa.platform.common.DatasetSourceModel;
import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.List;

@KeySpace("folder")
@Data
@NoArgsConstructor
public class FolderModel implements DatasetSourceModel<Long> {
    @Id
    @GeneratedSequence("folder-id")
    private Long id;
    private String name;
    private String description;
    private List<LabelModel> labels;
    private Long parent;
    private long dataset;
    private String project;

    public FolderModel(String name, String project) {
        this.name = name;
        this.project = project;
    }

    public FolderModel(String name, String description, Long parent, String project) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.project = project;
    }
}
