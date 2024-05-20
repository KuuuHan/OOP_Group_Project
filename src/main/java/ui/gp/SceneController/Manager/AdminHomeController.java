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
import ui.gp.Models.Role;
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.AdminController;
import ui.gp.SceneController.Controllers.DependentController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.LoadingSceneController;
import ui.gp.SceneController.Function.PolicyHolderUpdatingFormController;
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
    @FXML
    ComboBox<String> FilterUserBox;
    @FXML
    Button showDetailButton;
    @FXML
    Button profileSave;
    @FXML
    Button profileReset;

    private SystemAdmin systemAdmin;
    private AdminController adminController;
    DatabaseConnection databaseConnection;

    // Fields to store the original values
    private String originalId;
    private String originalFullname;
    private String originalUsername;
    private String originalPassword;
    private String originalEmail;
    private String originalPhonenumber;
    private String originalAddress;
    private User selectedUser;
    private ViewFactory view;

    //======================================


    public AdminHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

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

        SystemAdminTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUser = (User) newSelection;
//                deleteBeneficiaryButton.setDisable(false);
                showDetailButton.setDisable(false);
//                updateBeneficiaryButton.setDisable(false);
            } else {
//                deleteBeneficiaryButton.setDisable(true);
                showDetailButton.setDisable(true);
//                updateBeneficiaryButton.setDisable(true);
            }
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            populateSystemAdminTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        List<String> filterOptions = new ArrayList<>();
        filterOptions.add("All");
        filterOptions.add("Dependent");
        filterOptions.add("Policy Holder");
        filterOptions.add("Insurance Manager");
        filterOptions.add("Insurance Surveyor");
        filterOptions.add("System Admin");
        filterOptions.add("Policy Owner");
        FilterUserBox.setItems(FXCollections.observableArrayList(filterOptions));
        FilterUserBox.setValue(filterOptions.get(0));

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


    @FXML
    public void onFilterBox(ActionEvent event) {
        String filter = FilterUserBox.getSelectionModel().getSelectedItem();
        if (filter != null) {
            if (filter.equals("All")) {
                SystemAdminTable.setItems(FXCollections.observableArrayList(adminController.retrieveSystemAccount()));
            } else {
                ObservableList<User> filteredData = FXCollections.observableArrayList();
                for (User user : adminController.retrieveSystemAccount()) {
                    if (user.getRole().name().equals(filter.replace(" ", "_"))) {
                        filteredData.add(user);
                    }
                }
                SystemAdminTable.setItems(filteredData);
            }
        }
    }

    @FXML
    public void onShowDetail(ActionEvent event){
        if(selectedUser != null){
            SystemAdminTable.getSelectionModel().clearSelection();
            showDetailButton.setDisable(true);
        } if(selectedUser.getRole().name().equals("Dependent")){
            view. showDependentInformation(selectedUser);
        } else if (selectedUser.getRole().name().equals("Policy_Holder")){
            view. showPolicyHolderInformation(selectedUser);
        } else if (selectedUser.getRole().name().equals("Insurance_Manager")){
            view.  showPolicyHolderInformation(selectedUser);
        } else if (selectedUser.getRole().name().equals("Insurance_Surveyor")){
            view.  showPolicyHolderInformation(selectedUser);
        } else if (selectedUser.getRole().name().equals("System_Admin")){
            view.  showPolicyHolderInformation(selectedUser);
        } else if (selectedUser.getRole().name().equals("Policy_Owner")){
            view.  showPolicyHolderInformation(selectedUser);
        }
    }

    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = passwordFieldInfo.getText();
        String email = emailFieldInfo.getText();
        String phoneNumber = phonenumberFieldInfo.getText();
        String address = addressFieldInfo.getText();
        String username = usernameFieldInfo.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        handleProfileTabSelection();
    }

    private void updateProfile(String password, String email, String phoneNumber, String address,String username)
    {
        try {
            String query = "UPDATE Users SET password = ?, email = ?, phoneNumber = ?, address = ? WHERE username = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);

            statement.setString(1, password);
            statement.setString(2, email);
            statement.setString(3, phoneNumber);
            statement.setString(4, address);
            statement.setString(5, username);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
