package apps.amaralus.qa.platform.project.linked;

import apps.amaralus.qa.platform.common.CrudService;
import apps.amaralus.qa.platform.common.model.IdSource;
import apps.amaralus.qa.platform.project.context.ProjectContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public abstract class ProjectLinkedService<E extends IdSource<I>, M extends ProjectLinkedModel<I>, I>
        extends CrudService<E, M, I> {

    protected ProjectContext projectContext;
    protected ProjectLinkedRepository<M, I> linkedRepository;

    @Override
    protected void beforeCreate(M model) {
        model.setProject(projectContext.getProjectId());
    }

    @Override
    public Optional<E> findById(@NotNull I id) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        return repository.findById(id)
                .filter(model -> model.getProject().equals(projectContext.getProjectId()))
                .map(mapper::toEntity);
    }

    @Override
    public List<E> findAll() {
        return linkedRepository.findAllByProject(projectContext.getProjectId()).stream()
                .map(mapper::toEntity)
                .toList();
    }

    public void deleteAllByProject() {
        linkedRepository.findAllByProject(projectContext.getProjectId())
                .forEach(model -> delete(model.getId()));
    }

    @Autowired
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }

    @Autowired
    public void setLinkedRepository(ProjectLinkedRepository<M, I> linkedRepository) {
        this.linkedRepository = linkedRepository;
    }
}
