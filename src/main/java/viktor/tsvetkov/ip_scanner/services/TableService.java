package viktor.tsvetkov.ip_scanner.services;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import java.util.List;

public class TableService {

    private final TableView<NetworkNode> table;

    public TableService(List<NetworkNode> nodes) {
        this.table = new TableView<>(FXCollections.observableList(nodes));
    }

    @SuppressWarnings("unchecked")
    public TableView<NetworkNode> createTable() {
        TableColumn<NetworkNode, String> addressColumn = new TableColumn<>("IP-адрес");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        TableColumn<NetworkNode, Boolean> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("online"));
        table.getColumns().addAll(addressColumn, statusColumn);
        return table;
    }
}
