package apps.amaralus.qa.platform.runtime.execution;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceAware {

    void setExecutorService(ExecutorService executorService);
}
