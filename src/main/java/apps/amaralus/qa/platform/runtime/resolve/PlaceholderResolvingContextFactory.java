package apps.amaralus.qa.platform.runtime.resolve;

import apps.amaralus.qa.platform.dataset.DatasetService;
import apps.amaralus.qa.platform.dataset.alias.AliasService;
import apps.amaralus.qa.platform.dataset.alias.model.AliasModel;
import apps.amaralus.qa.platform.dataset.model.DatasetModel;
import apps.amaralus.qa.platform.folder.model.FolderModel;
import apps.amaralus.qa.platform.project.database.ProjectModel;
import apps.amaralus.qa.platform.testcase.model.TestCaseModel;
import apps.amaralus.qa.platform.testplan.TestPlan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaceholderResolvingContextFactory {

    private final DatasetService datasetService;
    private final AliasService aliasService;

    public Factory createForTestPlan(TestPlan testPlan) {
        return new Factory(testPlan, datasetService.findAllModels(), aliasService.findAllModels());
    }

    @RequiredArgsConstructor
    public static class Factory {
        private final TestPlan testPlan;
        private final List<DatasetModel> datasets;
        private final List<AliasModel> aliases;
        private DatasetModel projectDataset;
        @Getter
        private DatasetModel testCaseDataset;
        private Map<Long, Optional<DatasetModel>> foldersDatasets;
        private Map<Long, Optional<DatasetModel>> allDatasets;

        public RuntimeResolvingContext produce(Long testCaseId) {
            var resolvingContext = new RuntimeResolvingContext();

            resolvingContext.setAliases(aliases.stream().
                    collect(Collectors.toConcurrentMap(
                            AliasModel::getName,
                            Optional::of)));

            filterDatasets(testCaseId);

            resolvingContext.setAllDatasets(allDatasets);
            resolvingContext.setFoldersDatasets(foldersDatasets);
            resolvingContext.setProjectDataset(projectDataset);
            resolvingContext.setTestCaseDataset(testCaseDataset);

            return resolvingContext;
        }

        private void filterDatasets(Long testCaseId) {
            allDatasets = new ConcurrentHashMap<>();
            foldersDatasets = new ConcurrentHashMap<>();

            for (var dataset : datasets) {
                if (dataset.isLinked()) {
                    var backlink = dataset.getBacklink();
                    if (backlink.modelCLass() == TestCaseModel.class && (testCaseId == backlink.id())) {
                        putDataset(dataset);
                        testCaseDataset = dataset;
                    } else if (backlink.modelCLass() == ProjectModel.class) {
                        putDataset(dataset);
                        projectDataset = dataset;
                        // todo сделать выявление цепочки папок и сравнение на попадание в нее
                    } else if (backlink.modelCLass() == FolderModel.class) {
                        putDataset(dataset);
                        foldersDatasets.put((Long) backlink.id(), Optional.of(dataset));
                    }
                } else
                    putDataset(dataset);
            }
        }

        private void putDataset(DatasetModel dataset) {
            allDatasets.put(dataset.getId(), Optional.of(dataset));
        }
    }
}
