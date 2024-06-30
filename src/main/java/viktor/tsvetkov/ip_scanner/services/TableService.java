package viktor.tsvetkov.ip_scanner.services;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import java.util.List;

public class TableService {

    private final TableView<NetworkNode> table;

    public TableService(List<NetworkNode> nodes) {
        this.table = new TableView<>(FXCollections.observableList(nodes));
    }

    public void setNodes(List<NetworkNode> nodes) {
        table.setItems(FXCollections.observableList(nodes));
        table.refresh();
    }

    @SuppressWarnings("unchecked")
    public TableView<NetworkNode> createTable() {
        TableColumn<NetworkNode, String> addressColumn = new TableColumn<>("IP-адрес");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        TableColumn<NetworkNode, Boolean> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("online"));
        TableColumn<NetworkNode, String> delayTimeColumn = new TableColumn<>("Время отклика (мс)");
        delayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("delayTimeMs"));
        TableColumn<NetworkNode, String> lastOnlineColumn = new TableColumn<>("Последняя активность");
        lastOnlineColumn.setCellValueFactory(new PropertyValueFactory<>("lastOnlineText"));
        table.getColumns().addAll(addressColumn, statusColumn, delayTimeColumn, lastOnlineColumn);
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
        return table;
    }
}
