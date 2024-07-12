package viktor.tsvetkov.ip_scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.controllers.ScanController;
import viktor.tsvetkov.ip_scanner.controllers.SettingsController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.model.ComponentsProvider;

import java.io.IOException;

@Slf4j
@SuppressWarnings("FieldCanBeLocal")
public class IPScannerApplication extends Application {

    private BorderPane root;
    private FXMLLoader scanLoader;
    private FXMLLoader settingsLoader;
    private ScanController scanController;
    private SettingsController settingsController;
    private LauncherProperties properties;
    private Node scanNode;
    private Node settingsNode;
    private ComponentsProvider componentsProvider;

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        properties = new LauncherProperties();
        scanLoader = new FXMLLoader(IPScannerApplication.class.getResource("hello-view.fxml"));
        scanNode = scanLoader.load();
        scanController = scanLoader.getController();
        settingsLoader = new FXMLLoader(IPScannerApplication.class.getResource("settings-view.fxml"));
        settingsController = settingsLoader.getController();
        settingsNode = settingsLoader.load();
        settingsController = settingsLoader.getController();
        componentsProvider = new ComponentsProvider(
                root, settingsNode, settingsController,
                properties, scanNode, scanController
        );
        if (!properties.getMainHosts().isEmpty()) {
            root.setCenter(scanNode);
            scanController.initScannerService(componentsProvider);
        } else {
            root.setCenter(settingsNode);
            settingsController.init(componentsProvider);
        }
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("IPScanner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}