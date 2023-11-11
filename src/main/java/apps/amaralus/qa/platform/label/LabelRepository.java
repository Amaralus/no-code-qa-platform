package apps.amaralus.qa.platform.label;

import apps.amaralus.qa.platform.label.model.LabelModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface LabelRepository extends KeyValueRepository<LabelModel, String> {

    List<LabelModel> findAllByProject(String project);
}
