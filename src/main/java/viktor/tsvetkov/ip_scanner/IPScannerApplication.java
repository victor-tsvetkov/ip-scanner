package viktor.tsvetkov.ip_scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.controllers.ScanController;
import viktor.tsvetkov.ip_scanner.controllers.SettingsController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;

import java.io.IOException;

@Slf4j
public class IPScannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        LauncherProperties properties = new LauncherProperties();
//        FXMLLoader fxmlLoader = new FXMLLoader(IPScannerApplication.class.getResource("hello-view.fxml"));
//        root.setCenter(fxmlLoader.load());
//        ScanController scanController = fxmlLoader.getController();
//        scanController.initScannerService(properties.getMainHosts());
        FXMLLoader settingsLoader = new FXMLLoader(IPScannerApplication.class.getResource("settings-view.fxml"));
        root.setCenter(settingsLoader.load());
        SettingsController settingsController = settingsLoader.getController();
        settingsController.init(properties);
        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("IPScanner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}