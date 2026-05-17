module ip_scanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires org.slf4j;
    requires org.apache.commons.lang3;

    opens viktor.tsvetkov.ip_scanner to javafx.fxml;
    opens viktor.tsvetkov.ip_scanner.model;
    exports viktor.tsvetkov.ip_scanner;
    exports viktor.tsvetkov.ip_scanner.controllers;
    exports viktor.tsvetkov.ip_scanner.launcher;
    exports viktor.tsvetkov.ip_scanner.stores;
    exports viktor.tsvetkov.ip_scanner.model;
    opens viktor.tsvetkov.ip_scanner.controllers to javafx.fxml;
}