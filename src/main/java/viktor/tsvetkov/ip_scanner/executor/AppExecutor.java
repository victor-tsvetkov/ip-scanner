package viktor.tsvetkov.ip_scanner.executor;

import java.util.concurrent.Executor;

public interface AppExecutor extends Executor {
    boolean close();
}
