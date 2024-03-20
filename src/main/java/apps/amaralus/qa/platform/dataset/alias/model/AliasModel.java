package apps.amaralus.qa.platform.dataset.alias.model;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("alias")
//todo сделать составной ключ name+project
public class AliasModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("alias-sequence")
    private Long id;
    private String name;
    private long entityId;
    private String variable;
    private String project;


    public boolean isVariableAlias() {
        return variable != null;
    }
}
