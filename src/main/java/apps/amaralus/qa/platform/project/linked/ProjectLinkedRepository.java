package apps.amaralus.qa.platform.project.linked;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectLinkedRepository<T extends ProjectLinkedModel<I>, I>
        extends KeyValueRepository<T, I> {

    List<T> findAllByProject(String project);

    Optional<T> findByIdAndProject(I id, String project);
}
