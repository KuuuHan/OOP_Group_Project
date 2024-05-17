package ui.gp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.gp.Models.Model;

import java.io.IOException;

public class ApplicationStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getView().showLoginScene();
    }

}