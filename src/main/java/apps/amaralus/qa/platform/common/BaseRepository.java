package apps.amaralus.qa.platform.common;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseModel<I>, I> extends KeyValueRepository<T, I> {

    List<T> findAllByProject(String project);

    Optional<T> findByIdAndProject(I id, String project);
}
