package viktor.tsvetkov.ip_scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.constants.Constants;
import viktor.tsvetkov.ip_scanner.controllers.MainController;
import viktor.tsvetkov.ip_scanner.launcher.LauncherProperties;
import viktor.tsvetkov.ip_scanner.model.SceneEntity;
import viktor.tsvetkov.ip_scanner.stores.SceneStore;

import java.io.IOException;

@Slf4j
public class IPScannerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneStore sceneStore = new SceneStore();
        LauncherProperties properties = new LauncherProperties();
        FXMLLoader mainLoader = new FXMLLoader(IPScannerApplication.class.getResource("main-view.fxml"));
        Region root = mainLoader.load();
        Scene scene = new Scene(root, 1070, 600);

        MainController mainController = mainLoader.getController();
        mainController.init(stage, properties, sceneStore);
        sceneStore.addSceneEntity(new SceneEntity("main-view.fxml", scene));
        stage.setTitle(Constants.MAIN_TITLE);
        stage.setResizable(true);
        stage.setScene(scene);
        letterbox(scene, root);
        stage.show();

        FXMLLoader settingsLoader = new FXMLLoader(IPScannerApplication.class.getResource("settings-view.fxml"));
        Region settingsRoot = settingsLoader.load();
        Scene settingsScene = new Scene(settingsRoot, 1070, 600);
        sceneStore.addSceneEntity(new SceneEntity("settings-view.fxml", settingsScene));
//        letterbox(settingsScene, settingsRoot);
//        stage.setScene(settingsScene);
//        stage.show();
    }

    private void letterbox(final Scene scene, final Region hBox) {
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
        private final Region hBox;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Region hBox) {
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