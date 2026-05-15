package viktor.tsvetkov.ip_scanner.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import viktor.tsvetkov.ip_scanner.constants.Constants;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;
import viktor.tsvetkov.ip_scanner.model.SceneEntity;
import viktor.tsvetkov.ip_scanner.services.ScannerService;
import viktor.tsvetkov.ip_scanner.stores.SceneStore;

public class MainController extends AbstractController {

    @FXML
    private GridPane gridHosts;
    @FXML
    private Button addBtn;
    @FXML
    private Button startScanBtn;
    @FXML
    private Button stopScanBtn;
    @FXML
    private Button openSettingsBtn;

    @FXML
    private TableView<NetworkNode> table;

    private ScannerService scannerService;

    @Override
    public void init(Stage stage, LauncherProperties properties, SceneStore sceneStore) {
        super.init(stage, properties, sceneStore);
        int rowIndex = 0;
        for (String host : properties.getMainHosts()) {
            addNode(rowIndex, host);
            rowIndex++;
        }
        addBtn.setOnAction(event -> addNode(gridHosts.getRowCount(), ""));
        scannerService = new ScannerService(table, properties.getMainHosts().toArray(new String[0]));
        table.setPlaceholder(new Label("Начните сканирование для отображения узлов в таблице"));
        startScanBtn.setOnAction(event -> {
            gridHosts.setDisable(true);
            addBtn.setDisable(true);
            startScanning();
        });
        stopScanBtn.setOnAction(event -> {
            gridHosts.setDisable(false);
            addBtn.setDisable(false);
            stopScanning();
        });
        openSettingsBtn.setOnAction(event -> openSettings());
    }

    private void startScanning() {
        if (!launcherProperties.getMainHosts().isEmpty()) {
            scannerService.updateHosts(launcherProperties.getMainHosts().toArray(new String[0]));
            scannerService.startScanning();
        }
    }

    private void openSettings() {
        SceneEntity sceneEntitySettings = sceneStore.search("settings-view.fxml");
        stage.setScene(sceneEntitySettings.getScene());
    }

    private void stopScanning() {
        scannerService.stopScanning();
    }

    private void addNode(int rowIndex, String host) {
        TextField textField = new TextField(host);
        textField.setDisable(true);
        Button saveBtn = new Button(Constants.ButtonConstants.SAVE);
        Button editBtn = new Button(Constants.ButtonConstants.EDIT);
        editBtn.setOnAction(event -> edit(rowIndex, 0));
        saveBtn.setOnAction(event -> save(rowIndex, 0));
        Button removeBtn = new Button(Constants.ButtonConstants.REMOVE);
        removeBtn.setOnAction(event -> {
            remove(rowIndex, 0);
            remove(rowIndex, 1);
            remove(rowIndex, 2);
            remove(rowIndex, 3);
        });
        gridHosts.add(textField, 0, rowIndex);
        gridHosts.add(saveBtn, 1, rowIndex);
        gridHosts.add(editBtn, 2, rowIndex);
        gridHosts.add(removeBtn, 3, rowIndex);
    }

    private void remove(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        gridHosts.getChildren().remove(node);
        if (columnIndex == 0) {
            TextField textField = (TextField) node;
            launcherProperties.removeAddress(textField.getText());
        }
    }

    private Node findNode(int rowIndex, int columnIndex) {
        ObservableList<Node> nodes = gridHosts.getChildren();
        return nodes.stream()
                .filter(item -> GridPane.getColumnIndex(item) == columnIndex && GridPane.getRowIndex(item) == rowIndex)
                .findFirst().orElseThrow();
    }

    private void save(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        TextField textField = (TextField) node;
        String newText = textField.getText();
        textField.setDisable(true);
        int index = GridPane.getRowIndex(node);
        launcherProperties.addAddress(newText, index);
    }

    private void edit(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        TextField textField = (TextField) node;
        textField.setDisable(false);
    }

}
