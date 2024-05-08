package apps.amaralus.qa.platform.environment.serviceapi.rest;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@KeySpace("rest-api-model")
public class RestApiModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("rest-api-id")
    private Long id;
    private String project;
    private Long itService;
    private Map<String, RestCallModel> restCalls = new HashMap<>();
}
