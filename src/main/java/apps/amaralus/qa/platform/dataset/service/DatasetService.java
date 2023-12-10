package apps.amaralus.qa.platform.dataset.service;

import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.repository.DatasetRepository;
import apps.amaralus.qa.platform.exception.EntityAlreadyExistsException;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import apps.amaralus.qa.platform.mapper.dataset.DatasetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;
    private final DatasetMapper datasetMapper;

    public void deleteAllByProject(String project) {
        datasetRepository.deleteAll(datasetRepository.findAllByProject(project));
    }

    public Dataset create(Dataset dataset) {

        var optional = datasetRepository.findByPathAndProject(dataset.path(), dataset.project());

        if (optional.isPresent()) {
            throw new EntityAlreadyExistsException(dataset.path());
        }

        var datasetModel = datasetMapper.mapToM(dataset);
        return datasetMapper.mapToD(datasetRepository.save(datasetModel));
    }

    public Dataset getByPath(String path, String project) {
        var datasetModel = datasetRepository.findByPathAndProject(path, project)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class));

        return datasetMapper.mapToD(datasetModel);
    }

    public Dataset getById(Long id) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, id.toString()));
        return datasetMapper.mapToD(datasetModel);
    }

    public Dataset updateDataset(Long id, Dataset dataset) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class, id.toString()));

        var updated = datasetMapper.update(datasetModel, dataset);

        return datasetMapper.mapToD(updated);
    }
}
