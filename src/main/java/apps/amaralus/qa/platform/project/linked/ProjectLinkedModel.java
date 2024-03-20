package apps.amaralus.qa.platform.project.linked;

import apps.amaralus.qa.platform.common.model.CrudModel;

public interface ProjectLinkedModel<I> extends CrudModel<I> {

    String getProject();

    void setProject(String project);
}
