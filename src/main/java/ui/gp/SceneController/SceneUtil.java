package ui.gp.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SceneUtil {
    private static AnchorPane currentScene;

    public static void setCurrentScene(AnchorPane currentScene) {
        SceneUtil.currentScene = currentScene;
    }

    public static void logout(AnchorPane pane) throws IOException {
        Optional<ButtonType> confirmation = confirmationDialog(
                "Logout",
                "Are you sure you want to logout?",
                "You will be redirected to the home page"
        );

        if (confirmation.get() == ButtonType.OK) {
            setCurrentScene(pane);
            changeScene (pane, "/ui/gp/Scene/Home.fxml");

            // luu lai thong tin
        }
    }

    private static void changeScene(AnchorPane currentScene, String fxmlPath) throws IOException {
        AnchorPane nextScene = FXMLLoader.load(Objects.requireNonNull(SceneUtil.class.getResource(fxmlPath)));
        changeCleanScene(currentScene, nextScene);
    }


    private static void changeCleanScene(AnchorPane currentScene, AnchorPane nextScene) {
        currentScene.getChildren().removeAll();
        currentScene.getChildren().setAll(nextScene);
    }


    private static Optional<ButtonType> confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

}
