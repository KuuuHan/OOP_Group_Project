package ui.gp.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.Buffer;

public class HomeController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane homeScene;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    Stage stage;

    @FXML
    public void login(ActionEvent loginAction) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            // cho nay cho reset mat khau ho
            // search nguoi o day

            if (username.equals("admin") && password.equals("admin")){
                System.out.println("Login Successful");
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Admin.fxml"));
                    stage = (Stage) homeScene.getScene().getWindow();
                    stage.getScene().setRoot(fxmlLoader.load());
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (username.equals("user") && password.equals("user")){
                System.out.println("Login Successful");
            }

            // khuc nay de login ne (link voi database hoac la lam txt luon idk?

        } else{
            loginErrorMsg();
        }
    }

    public void exit(ActionEvent exitAction){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        exitAlert.setHeaderText("You are about to exit the program");
        exitAlert.setContentText("Are you sure you want to exit?");

        if (exitAlert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) homeScene.getScene().getWindow();
            stage.close();
        }
    }

    private void loginErrorMsg(){
        Alert loginAlert = new Alert(Alert.AlertType.ERROR);
        loginAlert.setTitle("Login Error");
        loginAlert.setHeaderText("Invalid Username or Password");
        loginAlert.setContentText("Please try again!");
        loginAlert.showAndWait();
    }
}
