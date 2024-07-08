package viktor.tsvetkov.ip_scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.controllers.ScanController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;

import java.io.IOException;

@Slf4j
public class IPScannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(IPScannerApplication.class.getResource("hello-view.fxml"));
        root.setCenter(fxmlLoader.load());
        ScanController scanController = fxmlLoader.getController();
        LauncherProperties properties = new LauncherProperties();
        scanController.initScannerService(properties.getMainHosts());
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("IPScanner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}