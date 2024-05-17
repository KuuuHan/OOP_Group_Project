package ui.gp.SceneController.Function;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.gp.Models.Model;
import ui.gp.Models.Role;

import ui.gp.Models.Users.Dependent;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Policy.DependentsHomeController;

import java.io.IOException;

public class LoginControl {
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
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/Dependents.fxml"));
                        AnchorPane root = new AnchorPane();
                        loader.setRoot(root);
                        root = loader.load();
                        stage = (Stage) homeScene.getScene().getWindow();
                        stage.getScene().setRoot(root);
                        DependentsHomeController controller = loader.getController();
                        Dependent dependent = model.getDependent();
                        DependentController dependentController = new DependentController(dependent);
                        controller.initialize(dependent, dependentController);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Login Successful!");
                    break;
                case Policy_Owner:
                    try {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/PolicyOwner.fxml"));
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
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Policy/PolicyHolder.fxml"));
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
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Manager.fxml"));
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
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/Surveyor.fxml"));
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
                        loader.setLocation(getClass().getResource("/ui/gp/Scene/Manager/AdminHome.fxml"));
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
