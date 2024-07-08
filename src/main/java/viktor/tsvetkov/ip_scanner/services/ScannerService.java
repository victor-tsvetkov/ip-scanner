package viktor.tsvetkov.ip_scanner.services;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import viktor.tsvetkov.ip_scanner.executor.ScheduleExecutor;
import viktor.tsvetkov.ip_scanner.launcher.IPScanner;
import viktor.tsvetkov.ip_scanner.logging.LogFileManager;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import java.util.List;

public class ScannerService {

    private final VBox vBox;
    private IPScanner scanner;
    private ScheduleExecutor executor;
    private TableService tableService;
    private TableView<NetworkNode> table;
    private List<NetworkNode> networkNodes;

    public ScannerService(VBox vBox, String[] hosts) {
        this.vBox = vBox;
        init(hosts);
    }

    private void init(String[] hosts) {
        executor = new ScheduleExecutor(1, 4);
        LogFileManager logFileManager = new LogFileManager();
        logFileManager.createLogsFile();
        scanner = new IPScanner(hosts, logFileManager);
    }

    public void startScanning() {
        networkNodes = scanner.scanMainHosts();
        tableService = new TableService(networkNodes);
        table = tableService.createTable();
        vBox.getChildren().add(table);
        executor.execute(() -> {
            Platform.runLater(() -> {
                scanner.scanMainHosts();
                tableService.setNodes(networkNodes);
            });
        });
    }

    public void stopScanning() {
        executor.close();
    }

}
