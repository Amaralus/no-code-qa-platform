package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("alias")
//todo сделать составной ключ name+project
public class AliasModel {
    @Id
    @GeneratedSequence("alias-sequence")
    private long id;
    private String name;
    private long dataset;
    private long propertyName;
    private String project;
}
