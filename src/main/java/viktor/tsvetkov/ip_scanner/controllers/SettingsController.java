package viktor.tsvetkov.ip_scanner.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.stores.SceneStore;

public class SettingsController extends AbstractController {
    @FXML
    private Button btnToMainView;

    @Override
    public void init(Stage stage, LauncherProperties properties, SceneStore sceneStore) {
        super.init(stage, properties, sceneStore);
        btnToMainView.setOnAction(event -> openMainView());
    }

    private void openMainView() {
        Scene scene = sceneStore.search("main-view.fxml").getScene();
        stage.setScene(scene);
    }
}
