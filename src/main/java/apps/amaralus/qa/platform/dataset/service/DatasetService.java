package apps.amaralus.qa.platform.dataset.service;

import apps.amaralus.qa.platform.dataset.dto.Dataset;
import apps.amaralus.qa.platform.dataset.repository.DatasetRepository;
import apps.amaralus.qa.platform.exception.EntityAlreadyExistsException;
import apps.amaralus.qa.platform.mapper.dataset.DatasetMapper;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;
    private final DatasetMapper datasetMapper;

    public void deleteAllByProject(String project) {
        datasetRepository.deleteAll(datasetRepository.findAllByProject(project));
    }

    public Dataset create(Dataset dataset) {

        Optional<DatasetModel> optional = datasetRepository.findByPathAndProject(dataset.path(), dataset.project());
        if (optional.isPresent()) {
            throw new EntityAlreadyExistsException(dataset.path());
        }

        var datasetModel = datasetMapper.mapToM(dataset);
        return datasetMapper.mapToD(datasetRepository.save(datasetModel));
    }

    public Dataset getByPath(String alias, String project) {
        var datasetModel = datasetRepository.findByPathAndProject(alias, project)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName()));

        return datasetMapper.mapToD(datasetModel);
    }

    public Dataset getById(Long id) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName(), id.toString()));
        return datasetMapper.mapToD(datasetModel);
    }

    public Dataset updateDataset(Long id, Dataset dataset) {
        var datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName(), id.toString()));

        var updated = datasetMapper.update(datasetModel, dataset);

        return datasetMapper.mapToD(updated);
    }
}
