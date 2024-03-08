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

        var optional = datasetRepository.findByPathAndProject(dataset.path(), dataset.project());

        if (optional.isPresent()) {
            throw new EntityAlreadyExistsException(dataset.path());
        }

        var datasetModel = datasetMapper.mapToM(dataset);
        return datasetMapper.mapToD(datasetRepository.save(datasetModel));
    }

    public Optional<Dataset> getByPath(String path, String project) {
        return datasetRepository.findByPathAndProject(path, project)
                .map(datasetMapper::mapToD);
    }

    public Optional<Dataset> getById(Long id) {
        return datasetRepository.findById(id)
                .map(datasetMapper::mapToD);
    }

    public Dataset updateDataset(Long id, Dataset dataset) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, id));

        var updated = datasetMapper.update(datasetModel, dataset);

        return datasetMapper.mapToD(updated);
    }
}
