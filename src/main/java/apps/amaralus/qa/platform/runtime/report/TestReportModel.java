package apps.amaralus.qa.platform.runtime.report;

import apps.amaralus.qa.platform.project.linked.ProjectLinkedModel;
import apps.amaralus.qa.platform.rocksdb.sequence.GeneratedSequence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace("test-report")
public class TestReportModel implements ProjectLinkedModel<Long> {
    @Id
    @GeneratedSequence("test-report-id")
    private Long id;
    //возможность при редактировании давать имя и описание отчету после сохранения?
    private String name;
    private String description;
    private String project;
    private String report;
}
