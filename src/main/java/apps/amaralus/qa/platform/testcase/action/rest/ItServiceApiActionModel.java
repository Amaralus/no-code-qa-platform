package apps.amaralus.qa.platform.testcase.action.rest;

import apps.amaralus.qa.platform.environment.serviceapi.ServiceApiType;
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
@KeySpace("it-service-api-action")
public class ItServiceApiActionModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("api-action-id")
    private Long id;
    private Long testCase;
    private Long serviceApi;
    private ServiceApiType apiType;
    private String project;
}
