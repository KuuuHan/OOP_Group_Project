package ui.gp.SceneController.Policy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ui.gp.Models.Users.Dependent;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Function.LoadingSceneController;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;

public class DependentsHomeController {
    private Dependent dependent;
    private DependentController dependentController;

    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane dependentsHomeScene;
    @FXML
    private TextField idField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private Tab profileTab;

    public DependentsHomeController() {
    }

    public void initialize(Dependent dependent, DependentController dependentController) {
        this.dependent = dependent;
        this.dependentController = dependentController;
    }
    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void dependentsLogout(ActionEvent logoutAction) throws IOException {
        System.out.println("Dependents logout");
        SceneUtil.logout(dependentsHomeScene);
    }

    public void loadingTest(ActionEvent loadingTestEvent) throws IOException { //Mo phong server dang chay
        LoadingSceneController loadingSceneController = new LoadingSceneController();
        loadingSceneController.serverRespondingHold();
    }


    @FXML
    public void handleProfileTabSelection() {
        if (profileTab.isSelected()) {
            String[] information = dependentController.retrieveInformation().split("\n");
            idField.setText(information[0].split(": ")[1]);
            fullNameField.setText(information[1].split(": ")[1]);
            usernameField.setText(information[2].split(": ")[1]);
            passwordField.setText(information[3].split(": ")[1]);
            emailField.setText(information[4].split(": ")[1]);
            phoneField.setText(information[5].split(": ")[1]);
            addressField.setText(information[6].split(": ")[1]);
        }
    }
}

