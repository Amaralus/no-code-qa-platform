package apps.amaralus.qa.platform.testcase;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface LabelRepository extends KeyValueRepository<Label, String> {
}
