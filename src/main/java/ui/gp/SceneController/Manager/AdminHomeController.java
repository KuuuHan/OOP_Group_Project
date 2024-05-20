package ui.gp.SceneController.Manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ui.gp.Models.Claim;
import ui.gp.Models.Model;
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.AdminController;
import ui.gp.SceneController.Function.LoadingSceneController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;
import ui.gp.Database.DatabaseConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminHomeController {
    @FXML
    private AnchorPane adminHomeScene;
    @FXML
    private Label welcomeBannerUser;
    @FXML
    private Tab claimViewTab;
    @FXML
    private Tab infoTab;
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
    Button profileSave;
    @FXML
    Button profileReset;
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
    private Tab smallClaimTab;
    @FXML
    private TextField smallClaimSearch;
    @FXML
    private ComboBox<String> smallStatusFilter;
    @FXML
    private TableView smallClaimTable;
    @FXML
    private TableColumn smallClaimID;
    @FXML
    private TableColumn smallClaimAmount;
    @FXML
    private TableColumn smallInsuredPerson;
    @FXML
    private TableColumn smallClaimDate;
    @FXML
    private TableColumn smallExamDate;
    @FXML
    private TableColumn smallStatus;
    @FXML
    private Label totalClaimNumber;

    @FXML
    private Tab ClaimViewTab;
    @FXML
    private TextField claimAdminSearch;
    @FXML
    private ComboBox<String> claimFilter;
    @FXML
    private TableView adminClaimTable;
    @FXML
    private Button showClaimButton;
    @FXML
    private TableColumn adminClaimId;
    @FXML
    private TableColumn adminClaimAmount;
    @FXML
    private TableColumn adminInsuredPerson;
    @FXML
    private TableColumn adminClaimDate;
    @FXML
    private TableColumn adminExamDate;
    @FXML
    private TableColumn adminClaimStatus;
    @FXML
    private TableColumn adminInsuranceNumber;


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
    private int claimCount;

    //======================================


    public AdminHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(SystemAdmin systemAdmin, AdminController adminController) {
        bannerNameView(systemAdmin.getFullname());
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

        smallClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateSmallClaimTable();
            }
        });

        claimViewTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateClaimTable();
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


        List<String> smallClaimFilter = new ArrayList<>();
        smallClaimFilter.add("All");
        smallClaimFilter.add("Approved");
        smallClaimFilter.add("Pending");
        smallClaimFilter.add("Rejected");
        smallStatusFilter.setItems(FXCollections.observableArrayList(smallClaimFilter));
        smallStatusFilter.setValue(filterOptions.get(0));

        List<String> ClaimFilters = new ArrayList<>();
        ClaimFilters.add("All");
        ClaimFilters.add("Approved");
        ClaimFilters.add("Pending");
        ClaimFilters.add("Rejected");
        claimFilter.setItems(FXCollections.observableArrayList(ClaimFilters));
        claimFilter.setValue(filterOptions.get(0));

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

    public void onSmallStatusFilterBox(ActionEvent event) {
        String filter = smallStatusFilter.getSelectionModel().getSelectedItem();
        if (filter != null) {
            if (filter.equals("All")) {
                smallClaimTable.setItems(FXCollections.observableArrayList(adminController.retrieveClaims()));
            } else {
                ObservableList<Claim> filteredData = FXCollections.observableArrayList();
                for (Claim claim : adminController.retrieveClaims()) {
                    if (claim.getStatus().equals(filter.replace(" ", "_"))) {
                        filteredData.add(claim);
                    }
                }
                smallClaimTable.setItems(filteredData);
            }
        }
    }

    public void onBigStatusFilterBox(ActionEvent event) {
        String filter = claimFilter.getSelectionModel().getSelectedItem();
        if (filter != null) {
            if (filter.equals("All")) {
                adminClaimTable.setItems(FXCollections.observableArrayList(adminController.retrieveClaims()));
            } else {
                ObservableList<Claim> filteredData = FXCollections.observableArrayList();
                for (Claim claim : adminController.retrieveClaims()) {
                    if (claim.getStatus().equals(filter.replace(" ", "_"))) {
                        filteredData.add(claim);
                    }
                }
                adminClaimTable.setItems(filteredData);
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
        String[] information = adminController.retrieveInformation().split("\n");
        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, idFieldInfo.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idFieldInfo.setText(resultSet.getString("id"));
                fullnameFieldInfo.setText(resultSet.getString("fullname"));
                usernameFieldInfo.setText(resultSet.getString("username"));
                passwordFieldInfo.setText(resultSet.getString("password"));
                emailFieldInfo.setText(resultSet.getString("email"));
                phonenumberFieldInfo.setText(resultSet.getString("phoneNumber"));
                addressFieldInfo.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public void populateClaimTable() {

        List<Claim> claims = adminController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        adminClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        adminInsuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        adminClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        adminExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        adminClaimStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        adminInsuranceNumber.setCellValueFactory(new PropertyValueFactory<>("bankNumber"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);


        claimAdminSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPersonID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(adminClaimTable.comparatorProperty());
        adminClaimTable.setItems(sortedData);
    }

    public void populateSmallClaimTable() {

        List<Claim> claims = adminController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        smallClaimID.setCellValueFactory(new PropertyValueFactory<>("id"));
        smallClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        smallInsuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        smallClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        smallExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        smallStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        claimCount = 0;
        for (Claim claim: claims) {
            claimCount++;
        }

        totalClaimNumber.setText(Integer.toString(claimCount));
        smallClaimSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPersonID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(smallClaimTable.comparatorProperty());
        smallClaimTable.setItems(sortedData);
    }

}
