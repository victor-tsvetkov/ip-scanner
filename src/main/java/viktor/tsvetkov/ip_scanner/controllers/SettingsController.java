package viktor.tsvetkov.ip_scanner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.model.ComponentsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class SettingsController {

    @FXML
    private AnchorPane anchorPane;
    private ScrollPane scrollPane;
    private GridPane gridPane;
    private Button addHostButton;
    private final String header = "Настройка статических адресов для сканирования";
    private Button btnToScanner;
    private ComponentsProvider componentsProvider;

    public void init(ComponentsProvider componentsProvider) {
        this.componentsProvider = componentsProvider;
        Label headerLabel = new Label(header);
        headerLabel.setLayoutX(100);
        headerLabel.setLayoutY(29);
        btnToScanner = new Button("Сканер");
        addHostButton = new Button("Добавить");
        addHostButton.setLayoutX(400);
        addHostButton.setLayoutY(30);
        addHostButton.setMnemonicParsing(false);
        addHostButton.setOnAction(event -> addNode(gridPane.getRowCount(), ""));
        btnToScanner.setOnAction(event -> moveToScanner());

        gridPane = new GridPane();
        int rowIndex = 0;
        for (String host : componentsProvider.properties().getMainHosts()) {
            addNode(rowIndex, host);
            gridPane.setVgap(15);
            gridPane.setHgap(10);
            rowIndex++;
        }
        scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefViewportHeight(150);
        scrollPane.setPrefViewportWidth(500);
        scrollPane.setLayoutX(50);
        scrollPane.setLayoutY(100);
        scrollPane.setHvalue(0);
        List<Node> nodeList = new ArrayList<>(Arrays.asList(scrollPane, addHostButton, btnToScanner, headerLabel));
        ObservableList<Node> nodes = FXCollections.observableList(nodeList);
        anchorPane.getChildren().addAll(nodes);
    }

    private void moveToScanner() {
        componentsProvider.root().setCenter(componentsProvider.nodeScanner());
        componentsProvider.scanController().initScannerService(componentsProvider);
    }

    private void save(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        TextField textField = (TextField) node;
        String newText = textField.getText();
        textField.setDisable(true);
        int index = gridPane.getRowIndex(node);
        componentsProvider.properties().addAddress(newText, index);
    }

    private void addNode(int rowIndex, String host) {
        TextField textField = new TextField(host);
        textField.setDisable(true);
        Button saveBtn = new Button("Сохранить");
        Button editBtn = new Button("Редактировать");
        int finalRowIndex = rowIndex;
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit(finalRowIndex, 0);
            }
        });
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save(finalRowIndex, 0);
            }
        });
        Button removeBtn = new Button("Удалить");
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                remove(finalRowIndex, 0);
                remove(finalRowIndex, 1);
                remove(finalRowIndex, 2);
                remove(finalRowIndex, 3);
            }
        });
        gridPane.add(textField, 0, rowIndex);
        gridPane.add(saveBtn, 1, rowIndex);
        gridPane.add(editBtn, 2, rowIndex);
        gridPane.add(removeBtn, 3, rowIndex);
    }

    private void remove(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        gridPane.getChildren().remove(node);
        if (columnIndex == 0) {
            TextField textField = (TextField) node;
            componentsProvider.properties().removeAddress(textField.getText());
        }
    }

    private Node findNode(int rowIndex, int columnIndex) {
        ObservableList<Node> nodes = gridPane.getChildren();
        Node node = nodes.stream()
                .filter(item -> GridPane.getColumnIndex(item) == columnIndex && GridPane.getRowIndex(item) == rowIndex)
                .findFirst().orElseThrow();
        return node;
    }

    private void edit(int rowIndex, int columnIndex) {
        Node node = findNode(rowIndex, columnIndex);
        TextField textField = (TextField) node;
        textField.setDisable(false);
    }

}
