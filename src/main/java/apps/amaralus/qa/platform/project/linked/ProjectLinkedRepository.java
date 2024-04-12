package apps.amaralus.qa.platform.project.linked;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectLinkedRepository<M extends ProjectLinkedModel<I>, I>
        extends KeyValueRepository<M, I> {

    List<M> findAllByProject(String project);

    Optional<M> findByIdAndProject(I id, String project);
}
