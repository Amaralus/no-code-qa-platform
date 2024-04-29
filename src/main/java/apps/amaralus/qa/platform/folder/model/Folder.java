package apps.amaralus.qa.platform.folder.model;

import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.dataset.linked.DatasetSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Folder implements IdSource<Long>, DatasetSource {
    private Long id = 0L;
    private String name;
    private String description;
    private Set<Long> labels = new HashSet<>();
    private Long parent;
    private long dataset;
    private String project;

    public Folder(String name, String project) {
        this.name = name;
        this.project = project;
    }
}
