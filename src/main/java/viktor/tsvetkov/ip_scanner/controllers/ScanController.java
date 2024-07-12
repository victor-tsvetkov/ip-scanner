package viktor.tsvetkov.ip_scanner.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import viktor.tsvetkov.ip_scanner.model.ComponentsProvider;
import viktor.tsvetkov.ip_scanner.services.ScannerService;

@SuppressWarnings("all")
public class ScanController {

    @FXML
    private VBox mainVbox;
    private ScannerService scannerService;
    private Button btnToSettings;
    private ComponentsProvider componentsProvider;

    public void initScannerService(ComponentsProvider componentsProvider) {
        this.componentsProvider = componentsProvider;
        scannerService = new ScannerService(mainVbox, componentsProvider.properties()
                .getMainHosts().toArray(new String[0]));
        btnToSettings = new Button("Настройки");
        btnToSettings.setLayoutX(300);
        btnToSettings.setLayoutY(30);
        mainVbox.getChildren().add(btnToSettings);
        btnToSettings.setOnAction(event -> moveToSettings());
        startScanning();
    }

    private void moveToSettings() {
        componentsProvider.root().setCenter(componentsProvider.nodeSettings());
        scannerService.stopScanning();
        componentsProvider.settingsController().init(componentsProvider);
        unmount();
    }

    public void startScanning() {
        scannerService.startScanning();
    }

    private void unmount() {
        scannerService.stopScanning();
        scannerService = null;
    }

    @FXML
    public void deinitialize() {
        unmount();
    }

}