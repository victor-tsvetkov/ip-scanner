package viktor.tsvetkov.ip_scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.controllers.MainController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;

import java.io.IOException;

@Slf4j
public class IPScannerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LauncherProperties properties = new LauncherProperties();
        FXMLLoader mainLoader = new FXMLLoader(IPScannerApplication.class.getResource("main-view.fxml"));
        HBox root = mainLoader.load();
        Scene scene = new Scene(root, 1070, 600);
        MainController mainController = mainLoader.getController();
        mainController.init(properties);
        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scene.getRoot().getTransforms().setAll(scale);
        stage.setTitle("IPScanner");
        stage.setResizable(true);
        stage.setScene(scene);
        letterbox(scene, root);
        stage.show();
    }

    private void letterbox(final Scene scene, final HBox hBox) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;
        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, hBox);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final HBox hBox;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, HBox hBox) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.hBox = hBox;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth  = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                hBox.setPrefWidth(newWidth / scaleFactor);
                hBox.setPrefHeight(newHeight / scaleFactor);
            } else {
                hBox.setPrefWidth(Math.max(initWidth, newWidth));
                hBox.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}