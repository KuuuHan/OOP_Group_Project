package ui.gp.SceneController.Manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ui.gp.Models.Model;
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.AdminController;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.LoadingSceneController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;
import ui.gp.View.ViewFactory;
import ui.gp.Database.DatabaseConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminHomeController {
    @FXML
    private AnchorPane adminHomeScene;
    @FXML
    private Label welcomeBannerUser;
    @FXML
    private Tab infoTab;
    @FXML
    private Tab claimTab;
    @FXML
    private Tab AccountTab;
    @FXML
    private TextField idFieldInfo;
    @FXML
    private TextField fullnameFieldInfo;
    @FXML
    private TextField usernameFieldInfo;
    @FXML
    private TextField passwordFieldInfo;
    @FXML
    private TextField emailFieldInfo;
    @FXML
    private TextField phonenumberFieldInfo;
    @FXML
    private TextField addressFieldInfo;
    @FXML
    private TableView<User> SystemAdminTable;
    @FXML
    Button adminProfileSaveButton;
    @FXML
    TableColumn idAdminColumn;
    @FXML
    TableColumn fullnameColumn;
    @FXML
    TableColumn usernameColumn;
    @FXML
    TableColumn passwordColumn;
    @FXML
    TableColumn emailColumn;
    @FXML
    TableColumn phonenumColumn;
    @FXML
    TableColumn addressColumn;
    @FXML
    TableColumn roleColumn;

    private SystemAdmin systemAdmin;
    private AdminController adminController;

    // Fields to store the original values
    private String originalId;
    private String originalFullname;
    private String originalUsername;
    private String originalPassword;
    private String originalEmail;
    private String originalPhonenumber;
    private String originalAddress;
    private User selectedUser;
    //======================================

    public AdminHomeController(){ }

    public void initialize(SystemAdmin systemAdmin, AdminController adminController) {
        this.systemAdmin = systemAdmin;
        this.adminController = adminController;
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
        AccountTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateSystemAdminTable();

            }
        });

        // Disable save button when non field is changed
//        adminProfileSaveButton.disableProperty().bind(Bindings.createBooleanBinding(() -> !isAnyFieldChanged(),
//                idFieldInfo.textProperty(),
//                fullnameFieldInfo.textProperty(),
//                usernameFieldInfo.textProperty(),
//                passwordFieldInfo.textProperty(),
//                emailFieldInfo.textProperty(),
//                phonenumberFieldInfo.textProperty(),
//                addressFieldInfo.textProperty()));
    }

    private void populateSystemAdminTable() {
        List<User> users = adminController.retrieveSystemAccount();
        ObservableList<User> data = FXCollections.observableArrayList(users);

        idAdminColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        SystemAdminTable.setItems(data);
        if (selectedUser != null)
        {
            SystemAdminTable.getSelectionModel().select(selectedUser);
        }
    }

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    @FXML
    public void adminLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(adminHomeScene);
        System.out.println("Dependents logout");
    }

    public void loadingTest(ActionEvent loadingTestEvent) throws IOException { //Mo phong server dang chay
        LoadingSceneController loadingSceneController = new LoadingSceneController();
        loadingSceneController.serverRespondingHold();
    }


    @FXML
    public void handleProfileTabSelection() {
        if (infoTab.isSelected()) {
            String[] information = adminController.retrieveInformation().split("\n");
            idFieldInfo.setText(information[0].split(": ")[1]);
            fullnameFieldInfo.setText(information[1].split(": ")[1]);
            usernameFieldInfo.setText(information[2].split(": ")[1]);
            passwordFieldInfo.setText(information[3].split(": ")[1]);
            emailFieldInfo.setText(information[4].split(": ")[1]);
            phonenumberFieldInfo.setText(information[5].split(": ")[1]);
            addressFieldInfo.setText(information[6].split(": ")[1]);

            // Store the original values to check if any field has been changed
//            idFieldInfo.setText(originalId);
//            fullnameFieldInfo.setText(originalFullname);
//            usernameFieldInfo.setText(originalUsername);
//            passwordFieldInfo.setText(originalPassword);
//            emailFieldInfo.setText(originalEmail);
//            phonenumberFieldInfo.setText(originalPhonenumber);
//            addressFieldInfo.setText(originalAddress);
        }
    }

    // Method to check if any field has been changed
    private boolean isAnyFieldChanged() {
        return !idFieldInfo.getText().equals(originalId) ||
                !fullnameFieldInfo.getText().equals(originalFullname) ||
                !usernameFieldInfo.getText().equals(originalUsername) ||
                !passwordFieldInfo.getText().equals(originalPassword) ||
                !emailFieldInfo.getText().equals(originalEmail) ||
                !phonenumberFieldInfo.getText().equals(originalPhonenumber) ||
                !addressFieldInfo.getText().equals(originalAddress);
    }


}
