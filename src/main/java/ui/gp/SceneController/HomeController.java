package ui.gp.SceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.gp.Models.Model;
import ui.gp.Models.Role;

import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private TextField homeLoginName;
    @FXML
    private PasswordField homeLoginPassword;
    @FXML
    private Label statusText;
    private FXMLLoader loader;
    private Parent root;
    Stage stage;

    @FXML
    public void login(ActionEvent event)
    {
        String username = homeLoginName.getText();
        String password = homeLoginPassword.getText();

        Model model = Model.getInstance();
        model.evaluateUserCred(username, password);

        if (model.getLoginSuccess()) {
            Role role = model.getLoginRole();
            switch (role)
            {
                case Dependent:
                    try
                    {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Dependents.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful!");
                    break;
                case Policy_Owner:
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/PolicyOwner.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");
                    break;
                case Policy_Holder:
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/PolicyHolder.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");
                    break;
                case Insurance_Manager:
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Manager.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");
                    break;
                case Insurance_Surveyor:
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Customer/Surveyor.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful");
                    break;
                case System_Admin:
                    try
                    {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Admin/AdminHome.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful!");
                    break;
                default:
                    statusText.setText("Invalid role");
                    break;
            }
        } else {
            statusText.setText("Invalid username or password. Please try again!");
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
}
