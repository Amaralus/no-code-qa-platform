package apps.amaralus.qa.platform.runtime.action.debug;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface DebugActionRepository extends KeyValueRepository<DebugAction, Long> {
}
