package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("folder")
@Data
public class Folder {
    @Id
    @GeneratedSequence("folder-id")
    private long id;
    private String name;
    private String description;
    private long parent;
    private String project;
}
