package apps.amaralus.qa.platform.dataset;

import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.dataset.model.api.Dataset;
import apps.amaralus.qa.platform.project.linked.ProjectLinkedService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetService extends ProjectLinkedService<Dataset, DatasetModel, Long> {

    public void setVariable(@NotNull Long id, @NotNull String key, @Nullable Object value) {
        repository.findById(id)
                .ifPresent(dataset -> {
                    dataset.setVariable(key, value);
                    repository.save(dataset);
                });
    }
}
