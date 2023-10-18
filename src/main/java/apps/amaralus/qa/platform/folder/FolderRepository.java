package apps.amaralus.qa.platform.folder;

import apps.amaralus.qa.platform.folder.model.FolderModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface FolderRepository extends KeyValueRepository<FolderModel, Long> {
}
