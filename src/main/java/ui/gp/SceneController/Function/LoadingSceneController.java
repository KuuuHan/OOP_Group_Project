package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadingSceneController {
    private Stage stage;
    private LoadingSceneController loadingSceneController;
    @FXML
    AnchorPane loadingPane;
    public void openLoadingScene() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/LoadingScreen.fxml"));
            AnchorPane root = new AnchorPane();
            fxmlLoader.setRoot(root);
            fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Prevent user from interacting with other windows
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeLoadingScene() {
//        if (stage != null) {
//            stage.close();
//        }
        Platform.runLater(() -> {
                stage.close();
        });
    }

    public void serverRespondingHold(){
        openLoadingScene();

        new Thread(() -> {
            try {
                Thread.sleep(2000); // this is to simulate the server responding
                                         // handle the thread here
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            closeLoadingScene();
        }).start();
    }
}
