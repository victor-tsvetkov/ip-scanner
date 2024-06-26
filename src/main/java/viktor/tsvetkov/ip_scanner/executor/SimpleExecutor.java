package viktor.tsvetkov.ip_scanner.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleExecutor implements AppExecutor {

    private final ExecutorService service = Executors.newCachedThreadPool();

    @Override
    public void execute(Runnable task) {
        service.execute(task);
    }

    @Override
    public boolean close() {
        try {
            service.shutdown();
            return service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
