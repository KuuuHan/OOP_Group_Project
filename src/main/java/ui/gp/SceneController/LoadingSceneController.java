package ui.gp.SceneController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class LoadingSceneController {
    public void openLoadingScene() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/src/main/resources/ui/gp/Scene/LoadingScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
