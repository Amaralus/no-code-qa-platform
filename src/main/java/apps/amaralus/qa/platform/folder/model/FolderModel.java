package apps.amaralus.qa.platform.folder.model;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("folder")
@Data
@NoArgsConstructor
public class FolderModel {
    @Id
    @GeneratedSequence("folder-id")
    private long id;
    private String name;
    private String description;
    private Long parent;
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
