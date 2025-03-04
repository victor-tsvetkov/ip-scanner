module viktor.tsvetkov.ip_scanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires org.slf4j;
    requires org.slf4j.simple;

    opens viktor.tsvetkov.ip_scanner to javafx.fxml;
    exports viktor.tsvetkov.ip_scanner;
}