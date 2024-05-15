package ui.gp.SceneController;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SceneUtil {
    private static AnchorPane currentScene;
    public static void setCurrentScene(AnchorPane scene) {
        currentScene = scene;
    }
    public static void logout(AnchorPane pane) throws IOException {
        Optional<ButtonType> comfirmation = comfirmationDialog(
                "Logout",
                "Are you sure you want to logout?",
                "You will be redirected to the home page"
        );

        if (comfirmation.get() == ButtonType.OK) {
            changeScene (pane, "/ui/gp/Scene/Home.fxml");
            // luu lai thong tin
        }
    }

    private static void changeScene(AnchorPane pane, String fxmlPath) throws IOException {
        AnchorPane nextScene = FXMLLoader.load(Objects.requireNonNull(SceneUtil.class.getResource(fxmlPath)));
        changeCleanScene(pane, nextScene);
    }

    private static void changeCleanScene(AnchorPane currentScene, AnchorPane nextScene) {
        currentScene.getChildren().removeAll();
        currentScene.getChildren().setAll(nextScene);
    }


    private static Optional<ButtonType> comfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
