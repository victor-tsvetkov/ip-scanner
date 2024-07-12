package viktor.tsvetkov.ip_scanner.model;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import viktor.tsvetkov.ip_scanner.controllers.ScanController;
import viktor.tsvetkov.ip_scanner.controllers.SettingsController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;

public record ComponentsProvider(
        BorderPane root,
        Node nodeSettings,
        SettingsController settingsController,
        LauncherProperties properties,
        Node nodeScanner,
        ScanController scanController
) {
}
