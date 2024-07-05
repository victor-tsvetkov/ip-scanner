package viktor.tsvetkov.ip_scanner.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutor {

    private final ScheduledExecutorService service;
    private final int delaySec;

    public ScheduleExecutor(int threads, int delaySec) {
        this.service = new ScheduledThreadPoolExecutor(threads);
        this.delaySec = delaySec;
    }

    public boolean close() {
        service.shutdownNow();
        try {
            return service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(Runnable command) {
        service.scheduleWithFixedDelay(command, 0, delaySec, TimeUnit.SECONDS);
    }
}
