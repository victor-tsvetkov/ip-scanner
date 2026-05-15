package viktor.tsvetkov.ip_scanner.controllers;

import javafx.stage.Stage;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.stores.SceneStore;

public abstract class AbstractController {
    protected Stage stage;
    protected LauncherProperties launcherProperties;
    protected SceneStore sceneStore;

    public void init(Stage stage, LauncherProperties launcherProperties, SceneStore sceneStore) {
        this.stage = stage;
        this.launcherProperties = launcherProperties;
        this.sceneStore = sceneStore;
    }
}
