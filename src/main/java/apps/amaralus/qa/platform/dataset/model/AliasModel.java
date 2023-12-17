package apps.amaralus.qa.platform.dataset.model;

import apps.amaralus.qa.platform.rocksdb.key.CompoundKey;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("alias")
public class AliasModel {

    @Id
    @GeneratedSequence("alias-sequence")
    private Key key;
    private String name;
    private long dataset;
    private String propertyName;

    @Getter
    @Setter
    @CompoundKey
    public static final class Key {
        long id;
        String project;
    }
}
