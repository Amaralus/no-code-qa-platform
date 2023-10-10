package apps.amaralus.qa.platform.folder;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface FolderRepository extends KeyValueRepository<Folder, Long> {
}
