package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.dataset.mapper.DatasetMapper;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.exception.EntityNotFoundException;
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
        DatasetModel datasetModel = datasetMapper.toDatasetModel(dataset);
        return datasetMapper.toDataset(datasetRepository.save(datasetModel));
    }

    public Dataset getByAlias(String alias) {
        DatasetModel datasetModel = datasetRepository.findByAlias(alias)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName()));
        return datasetMapper.toDataset(datasetModel);
    }

    public Dataset getById(Long id) {
        DatasetModel datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName(), id.toString()));
        return datasetMapper.toDataset(datasetModel);
    }

    public Dataset updateDataset(Long id, Dataset dataset) {
        DatasetModel datasetModel = datasetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DatasetModel.class.getSimpleName(), id.toString()));

        DatasetModel updated = datasetMapper.update(datasetModel, dataset);

        return datasetMapper.toDataset(updated);
    }
}
