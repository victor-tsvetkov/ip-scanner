package viktor.tsvetkov.ip_scanner.services;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

public class TableService {

    private TableView<NetworkNode> tableView;

    public TableService(TableView<NetworkNode> table) {
        this.tableView = table;
    }

    @SuppressWarnings("unchecked")
    public void createTable() {
        TableColumn<NetworkNode, String> addressColumn = new TableColumn<>("IP-адрес");
        TableColumn<NetworkNode, Boolean> statusColumn = new TableColumn<>("Статус");
        TableColumn<NetworkNode, String> logColumn = new TableColumn<>("IP-адрес");
        TableColumn<NetworkNode, String> lastOnlineColumn = new TableColumn<>("Статус");
        tableView.getColumns().addAll(addressColumn, statusColumn, logColumn, lastOnlineColumn);
    }
}
