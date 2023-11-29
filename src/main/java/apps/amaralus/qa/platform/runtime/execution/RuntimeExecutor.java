package apps.amaralus.qa.platform.runtime.execution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Slf4j
@SuppressWarnings("java:S1948")
public class RuntimeExecutor extends CustomizableThreadFactory implements InitializingBean, DisposableBean {

    private ExecutorService executorService;

    public RuntimeExecutor() {
        super("testing-runtime-worker");
        setThreadGroupName("testing-runtime");
    }

    @Override
    public void afterPropertiesSet() {
        int availableThreads = Runtime.getRuntime().availableProcessors();
        log.debug("Initializing testing runtime executor. {} system threads available", availableThreads);

        executorService = new ThreadPoolExecutor(availableThreads, availableThreads * 2,
                1L, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(), this);
    }

    @Override
    public void destroy() {
        log.debug("Shutting down testing runtime executor");
        executorService.shutdown();
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executorService);
    }

    public CompletableFuture<Void> runAsync(CompletableFuture<?> completableFuture, Runnable runnable) {
        return completableFuture.thenRunAsync(runnable, executorService);
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorService);
    }

    public <T, R> CompletableFuture<R> supplyAsync(CompletableFuture<T> completableFuture, Function<T, R> function) {
        return completableFuture.thenApplyAsync(function, executorService);
    }
}
