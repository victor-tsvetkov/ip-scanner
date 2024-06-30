package viktor.tsvetkov.ip_scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import viktor.tsvetkov.ip_scanner.executor.ScheduleExecutor;
import viktor.tsvetkov.ip_scanner.launcher.IPScanner;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.logging.LogFileManager;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;
import viktor.tsvetkov.ip_scanner.services.TableService;
import viktor.tsvetkov.ip_scanner.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class ScanController {

    @FXML
    private VBox mainVbox;

    private IPScanner scanner;
    private ScheduleExecutor executor;
    private TableService tableService;
    private List<NetworkNode> networkNodes;
    private TableView<NetworkNode> table;

    @FXML
    public void initialize() {
        executor = new ScheduleExecutor(1, 4);
        LauncherProperties properties = new LauncherProperties();
        LogFileManager logFileManager = new LogFileManager();
        logFileManager.createLogsFile();
        scanner = new IPScanner(properties.getMainHosts(), logFileManager);
        scanMainHosts();
    }

    @FXML
    public void scanMainHosts() {
        networkNodes = scanner.scanMainHosts();
        tableService = new TableService(networkNodes);
        table = tableService.createTable();
        mainVbox.getChildren().add(table);
        executor.execute(() -> {
            Platform.runLater(() -> {
                scanner.scanMainHosts();
                tableService.setNodes(networkNodes);
            });
        });
    }
}