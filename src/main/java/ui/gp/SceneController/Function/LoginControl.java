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
import ui.gp.View.ViewFactory;

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
        ViewFactory view = new ViewFactory();
        model.evaluateUserCred(username, password);

        if (model.getLoginSuccess()) {
            Role role = model.getLoginRole();
            switch (role)
            {
                case Dependent:
                    view.showDependentWindow(model, homeScene);
                    break;
                case Policy_Owner:
                    view.showPolicyOwnerWindow(model, homeScene);
                    break;
                case Policy_Holder:
                    view.showPolicyHolderWindow(model, homeScene);
                    break;
                case Insurance_Manager:
                    view.showInsuranceManagerWindow(model, homeScene);
                    break;
                case Insurance_Surveyor:
                    view.showInsuranceSurveyorWindow(model, homeScene);
                    break;
                case System_Admin:
                    view.showSystemAdminWindow(model, homeScene);
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
