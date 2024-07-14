package viktor.tsvetkov.ip_scanner.services;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import viktor.tsvetkov.ip_scanner.executor.ScheduleExecutor;
import viktor.tsvetkov.ip_scanner.launcher.IPScanner;
import viktor.tsvetkov.ip_scanner.logging.LogFileManager;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import java.util.List;

public class ScannerService {

    private IPScanner scanner;
    private ScheduleExecutor executor;
    private final TableView<NetworkNode> table;
    private List<NetworkNode> networkNodes;

    public ScannerService(TableView<NetworkNode> table, String[] hosts) {
        this.table = table;
        init(hosts);
    }

    public void setNodes(List<NetworkNode> nodes) {
        table.setItems(FXCollections.observableList(nodes));
        table.refresh();
    }

    private void initColumns() {
        for (TableColumn<NetworkNode, ?> column : table.getColumns()) {
            switch (column.getText()) {
                case "IP-адрес":
                    column.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
                    break;
                case "Статус":
                    column.setCellValueFactory(new PropertyValueFactory<>("online"));
                    break;
                case "Время отклика (мс)":
                    column.setCellValueFactory(new PropertyValueFactory<>("delayTimeMs"));
                    break;
                case "Последняя активность":
                    column.setCellValueFactory(new PropertyValueFactory<>("lastOnlineText"));
                    break;
            }
        }
        table.setRowFactory(tr -> new TableRow<>() {
            @Override
            public void updateItem(NetworkNode networkNode, boolean empty) {
                super.updateItem(networkNode, empty);
                if (networkNode != null) {
                    if (networkNode.getOnline().equals("Не в сети")) {
                        setStyle("-fx-background-color: #E02840;");
                    } else {
                        if (networkNode.getDelayTimeMs() != null) {
                            if (Long.parseLong(networkNode.getDelayTimeMs()) > 150) {
                                setStyle("-fx-background-color: #E0C200;");
                            } else {
                                setStyle("-fx-background-color: #1EE009;");
                            }
                        }
                    }
                }
            }
        });
    }

    private void init(String[] hosts) {
        executor = new ScheduleExecutor(1, 4);
        LogFileManager logFileManager = new LogFileManager();
        logFileManager.createLogsFile();
        scanner = new IPScanner(hosts, logFileManager);
        initColumns();
    }

    public void updateHosts(String[] hosts) {
        scanner.updateHosts(hosts);
    }

    public void startScanning() {
        networkNodes = scanner.scanMainHosts();
        executor.execute(() -> {
            Platform.runLater(() -> {
                scanner.scanMainHosts();
                setNodes(networkNodes);
            });
        });
    }

    public void stopScanning() {
        executor.close();
        executor = new ScheduleExecutor(1, 4);
    }

}
