package apps.amaralus.qa.platform.runtime.execution;

import org.springframework.beans.factory.Aware;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceAware extends Aware {

    void setExecutorService(ExecutorService executorService);
}
