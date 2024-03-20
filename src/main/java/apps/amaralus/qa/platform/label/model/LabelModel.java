package apps.amaralus.qa.platform.label.model;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("label")
@Data
public class LabelModel implements ProjectLinkedModel<Long> {
        @Id
        @GeneratedSequence("label-id")
        private Long id;
        private String name;
        private String description;
        private String project;
}
