package viktor.tsvetkov.ip_scanner.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import viktor.tsvetkov.ip_scanner.services.ScannerService;

public class ScanController {

    @FXML
    private VBox mainVbox;
    private ScannerService scannerService;

    public void initScannerService(String[] hosts) {
        scannerService = new ScannerService(mainVbox, hosts);
        scannerService.startScanning();
    }

    @FXML
    public void deinitialize() {
        scannerService.stopScanning();
        scannerService = null;
    }

}