package apps.amaralus.qa.platform.folder.model;

import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import apps.amaralus.qa.platform.label.model.LabelModel;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.ArrayList;
import java.util.List;

@KeySpace("folder")
@Data
public class FolderModel implements ProjectLinkedModel<Long>, DatasetSource {
    @Id
    @GeneratedSequence("folder-id")
    private Long id;
    private String name;
    private String description;
    private List<LabelModel> labels = new ArrayList<>();
    private Long parent;
    private long dataset;
    private String project;
}
