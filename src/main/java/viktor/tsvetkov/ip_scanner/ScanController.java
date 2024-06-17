package viktor.tsvetkov.ip_scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import viktor.tsvetkov.ip_scanner.executor.ScheduleExecutor;
import viktor.tsvetkov.ip_scanner.executor.SimpleExecutor;
import viktor.tsvetkov.ip_scanner.launcher.IPScanner;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;
import viktor.tsvetkov.ip_scanner.utils.Color;

import java.util.List;

public class ScanController {

    @FXML
    private VBox mainVbox;

    private IPScanner scanner;
    private ScheduleExecutor executor;

    @FXML
    public void initialize() {
        executor = new ScheduleExecutor(1, 4);
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        LauncherProperties properties = new LauncherProperties();
        scanner = new IPScanner(properties.getMainHosts(), simpleExecutor);
        scanMainHosts();
        for (int i = 0; i < properties.getMainHosts().length; i++) {
            TextField textField = new TextField();
            textField.setId(properties.getMainHosts()[i]);
            mainVbox.getChildren().add(textField);
        }
    }

    @FXML
    public void scanMainHosts() {
        executor.execute(() -> {
            List<NetworkNode> networkNodes = scanner.scanMainHosts();
            Platform.runLater(() -> {
                networkNodes.forEach(networkNode -> {
                    TextField textField = (TextField) mainVbox
                            .getChildren().stream().filter(field -> field.getId().equals(networkNode.getIpAddress()))
                            .findFirst().orElseThrow();
                    String status = networkNode.isOnline() ? " в сети" : " не в сети";
                    String lastOnlineTime = "";
                    if (!networkNode.isOnline()) {
                        if (networkNode.getLastOnlineTime() == null) {
                            lastOnlineTime = ". Последняя активность: сегодня ещё не был активен";
                        } else {
                            lastOnlineTime = ". Последняя активность: " + networkNode.getLastOnlineTime();
                        }
                    }
                    textField.setText("Адрес " + networkNode.getIpAddress() + status + lastOnlineTime);
                    String color = networkNode.isOnline() ? Color.GREEN.getColor() : Color.RED.getColor();
                    textField.setStyle("-fx-control-inner-background: " + color);
                });
            });
        });
    }
}