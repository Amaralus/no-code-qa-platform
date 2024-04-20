package apps.amaralus.qa.platform.testcase.action.debug;


import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@AllArgsConstructor
@NoArgsConstructor
@KeySpace("debug-action")
public class DebugActionModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("debug-action-id")
    private Long id;
    private String project;
    private String message;
}
