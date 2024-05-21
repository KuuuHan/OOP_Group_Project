package ui.gp.SceneController.Manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.InsuranceSurveyor;
import ui.gp.Models.Users.Manager;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Controllers.SurveyorController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SurveyorHomeController {


    public Label welcomeBannerUser;
   
    public AnchorPane surveyorHomeScene;
    public Tab infoTab;
    public GridPane infoView;
    public TextField fullnameField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField phonenumberField;
    public TextField emailField;
    public TextField addressField;
    public TextField idField;
    private DatabaseConnection databaseConnection;
    private ViewFactory view;
    private Customer selectedCustomer;
    private Claim selectedClaim;
    private InsuranceSurveyor surveyor;
    private SurveyorController surveyorController;
    @FXML
    private Button surveyorViewClaim;

    @FXML
    Button managerViewCustomer;


    @FXML
    TableColumn surveyorClaimId;
    @FXML
    TableColumn surveyorInsuredPerson;
    @FXML
    TableColumn surveyorClaimAmount;
    @FXML
    TableColumn surveyorClaimStatus;
    @FXML
    TableColumn surveyorClaimDate;
    @FXML
    private TableColumn surveyorExamDate;

    @FXML
    private Button surveyorProposeClaim;

    @FXML
    TableView <Customer> customerSurveyorTable;
    @FXML
    TableColumn customerIDSurveyorView;
    @FXML
    TableColumn customerNameSurveyorView;
    @FXML
    TableColumn customerRoleSurveyorView;
    @FXML
    TableColumn customerPhoneSurveyorView;
    @FXML
    TableColumn customerEmailSurveyorView;
    @FXML
    TableColumn customerAddressSurveyorView;
    @FXML
    private TextField surveyorSearchClaim;
    @FXML
    private TextField surveyorSearchCustomer;
    @FXML
    private Tab managerViewCustomerTab;
    @FXML
    private Tab surveyorClaimTab;
    @FXML
    private TableView surveyorClaimTable;

    @FXML
    private TableView surveyorPendingClaimTable;
    @FXML
    private Tab surveyorPendingClaimTab;
    @FXML
    private TextField surveyorSearchPendingClaim;
    @FXML
    private TableColumn surveyorPendingClaimId;
    @FXML
    private TableColumn surveyorPendingInsuredPerson;
    @FXML
    private TableColumn surveyorPendingAmount;
    @FXML
    private TableColumn surveyorPendingClaimDate;

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void SurveyorController(){
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }

    public void initialize(InsuranceSurveyor surveyor, SurveyorController surveyorController) {
        bannerNameView(surveyor.getUsername());
        this.surveyor = surveyor;
        this.surveyorController = surveyorController;
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
        managerViewCustomerTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateCustomerSurveyorTable();
            }
        });

        surveyorPendingClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populatePendingClaimTable();
            }
        });

        customerSurveyorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCustomer = (Customer) newSelection;
                managerViewCustomer.setDisable(false);
            } else {
                managerViewCustomer.setDisable(true);
            }
        });

        surveyorClaimTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClaim = (Claim) newSelection;
                surveyorViewClaim.setDisable(false);
            } else {
                surveyorViewClaim.setDisable(true);
            }
        });

        surveyorPendingClaimTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClaim = (Claim) newSelection;
                surveyorProposeClaim.setDisable(false);
            } else {
                surveyorProposeClaim.setDisable(true);
            }
        });

        surveyorClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateSurveyorClaimTable();
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            populateCustomerSurveyorTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleProfileTabSelection() {
        if (infoTab.isSelected() && surveyorController != null) {
            String[] information = surveyorController.retrieveInformation().split("\n");
            idField.setText(information[0].split(": ")[1]);
            fullnameField.setText(information[1].split(": ")[1]);
            usernameField.setText(information[2].split(": ")[1]);
            passwordField.setText(information[3].split(": ")[1]);
            emailField.setText(information[4].split(": ")[1]);
            phonenumberField.setText(information[5].split(": ")[1]);
            addressField.setText(information[6].split(": ")[1]);
        }
    }

    @FXML
    public void SurveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(surveyorHomeScene);
        System.out.println("Insurance Surveyor logout");
    }

    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = passwordField.getText();
        String email = emailField.getText();
        String phoneNumber = phonenumberField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        String[] information = surveyorController.retrieveInformation().split("\n");
        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, idField.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idField.setText(resultSet.getString("id"));
                fullnameField.setText(resultSet.getString("fullname"));
                usernameField.setText(resultSet.getString("username"));
                passwordField.setText(resultSet.getString("password"));
                emailField.setText(resultSet.getString("email"));
                phonenumberField.setText(resultSet.getString("phoneNumber"));
                addressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateProfile(String password, String email, String phoneNumber, String address,String username)
    {
        try {
            String query = "UPDATE Users SET password = ?, email = ?, phonenumber = ?, address = ? WHERE username = ?";
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

    private void populateCustomerSurveyorTable(){
        List<Customer> customers = surveyorController.retrieveBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(customers);

        customerIDSurveyorView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameSurveyorView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleSurveyorView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneSurveyorView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailSurveyorView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressSurveyorView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customer.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customer.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        customerSurveyorTable.setItems(filteredData);
        if (selectedCustomer != null){
            customerSurveyorTable.getSelectionModel().select((Customer) selectedCustomer);
        }

    }

    public void populateSurveyorClaimTable() {
        List<Claim> Claim = surveyorController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(Claim);

        surveyorClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        surveyorInsuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPersonID"));
        surveyorClaimDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        surveyorExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        surveyorClaimStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        surveyorClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        surveyorClaimTable.setItems(filteredData);
        if (selectedClaim != null)
        {
            surveyorClaimTable.getSelectionModel().select(selectedClaim);
        }
    }

    public void populatePendingClaimTable() {
        List<Claim> Claim = surveyorController.retrievePendingClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(Claim);

        surveyorPendingClaimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        surveyorPendingInsuredPerson.setCellValueFactory(new PropertyValueFactory<>("insuredPersonID"));
        surveyorPendingClaimDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        surveyorPendingAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchPendingClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        surveyorPendingClaimTable.setItems(filteredData);
        if (selectedClaim != null)
        {
            surveyorPendingClaimTable.getSelectionModel().select(selectedClaim);
        }
    }

    @FXML
    public void onShowDetail(ActionEvent event){
        if(selectedCustomer != null){
            customerSurveyorTable.getSelectionModel().clearSelection();
            managerViewCustomer.setDisable(true);
        } if(selectedCustomer.getRole().name().equals("Dependent")){
            view.showDependentInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Holder")){
            view.showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Insurance_Surveyor")){
            view.showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Owner")){
            view.showPolicyHolderInformation(selectedCustomer);
        }
    }

    public void ProposeClaimButton()
    {
        if (selectedClaim != null) {
            surveyorPendingClaimTable.getSelectionModel().clearSelection();
            surveyorProposeClaim.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Your Proposal");
            alert.setHeaderText("Propose Claim");
            alert.setContentText("Are you sure you want to propose this claim?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                updateClaimStatus(selectedClaim, ClaimStatus.Pending);
                populatePendingClaimTable();
                populateSurveyorClaimTable();
            }
        }
    }

    private void updateClaimStatus(Claim selectedClaim, ClaimStatus status) {
        try {
            Thread deleteThread = new Thread(() -> {
                try {
                    String userQuery = "UPDATE claim SET claim_status = ? WHERE id = ?";
                    try (PreparedStatement userStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(userQuery)) {
                        userStatement.setString(1,status.toString());
                        userStatement.setString(2, selectedClaim.getId());
                        userStatement.executeUpdate();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            deleteThread.start();
            deleteThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showClaimButtonAction() {
        if (selectedClaim != null) {
            surveyorClaimTable.getSelectionModel().clearSelection();
            surveyorViewClaim.setDisable(true);
            view.showSpecificClaimForm(selectedClaim);
        }


    }
}


