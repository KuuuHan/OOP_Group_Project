package ui.gp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("/ui/gp/Scene/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Group Project");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/ui/gp/appIcon/appIcon.jpeg").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}