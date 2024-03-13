package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.common.exception.EntityAlreadyExistsException;
import apps.amaralus.qa.platform.common.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.project.context.ProjectLinked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatasetService extends ProjectLinked {

    private final DatasetRepository datasetRepository;
    private final DatasetMapper datasetMapper;

    @Override
    public void deleteAllByProject() {
        datasetRepository.deleteAll(datasetRepository.findAllByProject(projectContext.getProjectId()));
    }

    public Dataset create(Dataset dataset) {
        if (datasetRepository.existsById(dataset.id()))
            throw new EntityAlreadyExistsException(dataset.id());

        var datasetModel = datasetMapper.toModel(dataset);
        datasetModel.setProject(projectContext.getProjectId());
        return datasetMapper.toEntity(datasetRepository.save(datasetModel));
    }

    public Optional<Dataset> getById(Long id) {
        return datasetRepository.findById(id)
                .filter(model -> model.getProject().equals(projectContext.getProjectId()))
                .map(datasetMapper::toEntity);
    }

    public Dataset updateDataset(Long id, Dataset dataset) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, id));

        var updated = datasetMapper.update(datasetModel, dataset);

        return datasetMapper.toEntity(updated);
    }
}
