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
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Model;
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManagerHomeController {

    ObservableList<Customer> customers;
    @FXML
    private TableColumn claimAmountManagerView;

    @FXML
    private TableColumn claimDateManagerView;

    @FXML
    private TableColumn claimIDManagerView;

    @FXML
    private TableColumn customerAddressManagerView;

    @FXML
    private TableColumn customerEmailManagerView;

    @FXML
    private TableColumn customerIDManagerView;

    @FXML
    private TableColumn customerNameManagerView;

    @FXML
    private TableColumn customerPhoneManagerView;

    @FXML
    private TableColumn customerRoleManagerView;

    @FXML
    private TableColumn examDateManagerView;

    @FXML
    private TableColumn<Claim, Integer> insuredPeopleManagerView;

    @FXML
    private TextField managerAddress;

    @FXML
    private Button managerApprovalButton;

    @FXML
    private ComboBox<String> managerClaimFilter;

    @FXML
    private ComboBox<String> managerCustomerFilter;

    @FXML
    private Button managerDeclineButton;

    @FXML
    private TextField managerEmail;

    @FXML
    private AnchorPane managerHomeScene;

    @FXML
    private TextField managerID;

    @FXML
    private Tab managerInfoTab;

    @FXML
    private TextField managerName;

    @FXML
    private TextField managerPassword;

    @FXML
    private TextField managerPhone;

    @FXML
    private TextField managerSearchClaim;

    @FXML
    private TextField managerSearchCustomer;

    @FXML
    private TextField managerSearchSurveyor;

    @FXML
    private TextField managerUser;

    @FXML
    private Button managerViewClaim;

    @FXML
    private Button managerViewPendingClaim;

    @FXML
    private Tab managerViewClaimTab;

    @FXML
    private Tab managerViewPendingClaimTab;

    @FXML
    private Button managerViewCustomer;

    @FXML
    private Tab managerViewCustomerTab;

    @FXML
    private Tab managerViewSurveyorTab;

    @FXML
    private TableColumn statusManagerView;

    @FXML
    private TableColumn surveyorAddressManagerView;

    @FXML
    private TableColumn surveyorEmailManagerView;

    @FXML
    private TableColumn surveyorIDManagerView;

    @FXML
    private TableColumn surveyorNameManagerView;

    @FXML
    private TableColumn surveyorPhoneManagerView;

    @FXML
    private TableColumn claimDatePendingManagerView;

    @FXML
    private TableColumn claimIDPendingManagerView;

    @FXML
    private TableView claimPendingManagerTable;

    @FXML
    private TableColumn examDatePendingManagerView;

    @FXML
    private TableColumn insuredPeoplePendingManagerView;

    @FXML
    private ComboBox<String> managerClaimPendingFilter;


    @FXML
    private TextField managerSearchPendingClaim;

    @FXML
    private TableColumn claimAmountPendingManagerView;


    @FXML
    private TableColumn statusManagerPendingView;

    @FXML
    private Label welcomeBannerUser;
    @FXML
    private TableView<Customer> customerManagerTable;
    @FXML
    private TableView<Provider> surveyorManagerTable;
    @FXML
    private TableView<Claim> claimManagerTable;
    private Manager manager;
    private ManagerController managerController;
    private String search;
    private final   ObservableList<Customer> dataList = FXCollections.observableArrayList();
    private Claim selectedClaim;
    private User selectedCustomer;
    private DatabaseConnection databaseConnection;
    private ViewFactory view;

    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username + "!");
    }

    public void initialize(Manager manager, ManagerController managerController) {
        bannerNameView(manager.getFullname());
        this.manager = manager;
        this.managerController = managerController;
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
        if (managerInfoTab.isSelected()) {
            handleProfileTabSelection();
        }
        managerViewCustomerTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateCustomerTable();
            }
        });

        List<String> displayComboList = new ArrayList<>();
        displayComboList.add("All");
        displayComboList.add("Policy Holder");
        displayComboList.add("Dependent");
        managerCustomerFilter.setItems(FXCollections.observableArrayList(displayComboList));
        managerCustomerFilter.setValue(displayComboList.get(0));

        managerViewSurveyorTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateSurveyorTable();
            }

        });

        managerViewClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateClaimTable();
            }

        });

        managerViewPendingClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populatePendingClaimTable();
            }

        });

        List<String> displayClaimComboList = new ArrayList<>();
        displayClaimComboList.add("All");
        displayClaimComboList.add("Rejected");
        displayClaimComboList.add("Approved");
        displayClaimComboList.add("Pending");
        managerClaimFilter.setItems(FXCollections.observableArrayList(displayClaimComboList));
        managerClaimFilter.setValue(displayClaimComboList.get(0));

        claimPendingManagerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClaim = (Claim) newSelection;
                managerApprovalButton.setDisable(false);
                managerDeclineButton.setDisable(false);
                managerViewClaim.setDisable(false);
            } else {
                managerApprovalButton.setDisable(true);
                managerDeclineButton.setDisable(true);
                managerViewClaim.setDisable(true);
            }
        });

        customerManagerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCustomer = (User) newSelection;
                managerViewCustomer.setDisable(false);
            } else {
                managerViewCustomer.setDisable(true);
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), event -> {
            populateCustomerTable();
            populateSurveyorTable();
            populatePendingClaimTable();
            populateClaimTable();
        }));


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }




    @FXML
    public void handleProfileTabSelection() {
        if (managerInfoTab.isSelected() && managerController != null) {
            String[] information = managerController.retrieveInformation().split("\n");
            managerID.setText(information[0].split(": ")[1]);
            managerName.setText(information[1].split(": ")[1]);
            managerUser.setText(information[2].split(": ")[1]);
            managerPassword.setText(information[3].split(": ")[1]);
            managerEmail.setText(information[4].split(": ")[1]);
            managerPhone.setText(information[5].split(": ")[1]);
            managerAddress.setText(information[6].split(": ")[1]);
        }
    }

    public void populateCustomerTable() {

        List<Customer> beneficiaries = managerController.retrieveBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(beneficiaries);

        customerIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameManagerView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleManagerView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneManagerView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailManagerView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressManagerView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

            managerSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(benefeciaries -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (benefeciaries.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (benefeciaries.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (benefeciaries.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else return false;
                });
            });

            SortedList<Customer> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(customerManagerTable.comparatorProperty());
            customerManagerTable.setItems(sortedData);
    }

    public void populateHolderTable() {

        List<Customer> beneficiaries = managerController.retrieveHolderBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(beneficiaries);

        customerIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameManagerView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleManagerView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneManagerView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailManagerView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressManagerView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(benefeciaries -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (benefeciaries.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (benefeciaries.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (benefeciaries.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(customerManagerTable.comparatorProperty());
        customerManagerTable.setItems(sortedData);
    }
    public void populateDependentTable() {

        List<Customer> beneficiaries = managerController.retrieveDependentBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(beneficiaries);

        customerIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameManagerView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleManagerView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneManagerView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailManagerView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressManagerView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(benefeciaries -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (benefeciaries.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (benefeciaries.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (benefeciaries.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });


        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(customerManagerTable.comparatorProperty());
        customerManagerTable.setItems(sortedData);
    }


    public void populateSurveyorTable() {

        List<Provider> benefeciaries = managerController.retrieveSurveyor();
        ObservableList<Provider> dataList = FXCollections.observableArrayList(benefeciaries);

        surveyorIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        surveyorNameManagerView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        surveyorPhoneManagerView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        surveyorEmailManagerView.setCellValueFactory(new PropertyValueFactory<>("email"));
        surveyorAddressManagerView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Provider> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchSurveyor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(beneficiaries -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (beneficiaries.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (beneficiaries.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (beneficiaries.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Provider> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(surveyorManagerTable.comparatorProperty());
        surveyorManagerTable.setItems(sortedData);
    }

    public void populateClaimTable() {

        List<Claim> claims = managerController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeopleManagerView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusManagerView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDateManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(claimManagerTable.comparatorProperty());
        claimManagerTable.setItems(sortedData);
    }



    public void populateRejectedFilterClaimTable() {

        List<Claim> claims = managerController.retrieveRejectedClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeopleManagerView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusManagerView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDateManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(claimManagerTable.comparatorProperty());
        claimManagerTable.setItems(sortedData);
    }

    public void populateApprovedFilterClaimTable() {

        List<Claim> claims = managerController.retrieveApprovedClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeopleManagerView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusManagerView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDateManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(claimManagerTable.comparatorProperty());
        claimManagerTable.setItems(sortedData);
    }

    public void populatePendingClaimTable() {

        List<Claim> claims = managerController.retrievePendingClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDPendingManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeoplePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountPendingManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusManagerPendingView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDatePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDatePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchPendingClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPersonID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(claimPendingManagerTable.comparatorProperty());
        claimPendingManagerTable.setItems(sortedData);
    }

    public void populatePendingFilterClaimTable() {

        List<Claim> claims = managerController.retrievePendingClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDPendingManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeoplePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountPendingManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusManagerPendingView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDatePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDatePendingManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        managerSearchPendingClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPersonID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(claimManagerTable.comparatorProperty());
        claimManagerTable.setItems(sortedData);
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(managerHomeScene);
        System.out.println("Insurance Manager logout");
    }


    @FXML
    protected void customerDisplayComboBox(ActionEvent event){
        String result = "Displayed all ";

        switch (managerCustomerFilter.getSelectionModel().getSelectedItem()){
            case "All":
                populateCustomerTable();
                break;
            case "Policy Holder":
                populateHolderTable();
                break;
            case "Dependent":
                populateDependentTable();
                break;
        }
    }

    @FXML
    protected void claimDisplayComboBox(ActionEvent event){
        String result = "Displayed all ";

        switch (managerClaimFilter.getSelectionModel().getSelectedItem()){
            case "All":
                populateClaimTable();
                break;
            case "Rejected":
                populateRejectedFilterClaimTable();
                break;
            case "Pending":
                populatePendingFilterClaimTable();
                break;
            case "Approved":
                populateApprovedFilterClaimTable();
                break;
        }
    }


    public void ApproveClaimButton()
    {
        if (selectedClaim != null) {
            claimManagerTable.getSelectionModel().clearSelection();
            managerApprovalButton.setDisable(true);
            managerDeclineButton.setDisable(true);
            managerViewClaim.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Your Approval");
            alert.setHeaderText("Approve Claim");
            alert.setContentText("Are you sure you want to approve this claim?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                updateClaimStatus(selectedClaim, ClaimStatus.Approved);
                populatePendingClaimTable();
                populateClaimTable();
            }
        }
    }

    public void DeclineClaimButton()
    {
        if (selectedClaim != null) {
            claimManagerTable.getSelectionModel().clearSelection();
            managerApprovalButton.setDisable(true);
            managerDeclineButton.setDisable(true);
            managerViewClaim.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Your Rejection");
            alert.setHeaderText("Reject Claim");
            alert.setContentText("Are you sure you want to reject this claim?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                updateClaimStatus(selectedClaim, ClaimStatus.Rejected);
                populatePendingClaimTable();
                populateClaimTable();
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

    @FXML
    public void showCustomerManager() {
        if (selectedCustomer != null) {
            customerManagerTable.getSelectionModel().clearSelection();

            managerViewCustomer.setDisable(true);
            if (selectedCustomer.getRole().name().equals("Dependent")) {
                view.showDependentInformation(selectedCustomer);
            } else {
                view.showPolicyHolderInformation(selectedCustomer);
            }
        }
    }

    @FXML
    public void onProfileSaveButton(ActionEvent event){

        String password = managerPassword.getText();
        String email = managerEmail.getText();
        String phoneNumber = managerPhone.getText();
        String address = managerAddress.getText();
        String username = managerUser.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        String[] information = managerController.retrieveInformation().split("\n");
        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, managerID.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                managerID.setText(resultSet.getString("id"));
                managerName.setText(resultSet.getString("fullname"));
                managerUser.setText(resultSet.getString("username"));
                managerPassword.setText(resultSet.getString("password"));
                managerEmail.setText(resultSet.getString("email"));
                managerPhone.setText(resultSet.getString("phoneNumber"));
                managerAddress.setText(resultSet.getString("address"));
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

    @FXML
    public void onShowDetail(ActionEvent event){
        if(selectedCustomer != null){
            customerManagerTable.getSelectionModel().clearSelection();
            managerViewCustomer.setDisable(true);
        } if(selectedCustomer.getRole().name().equals("Dependent")){
            view. showDependentInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Holder")){
            view. showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Insurance_Manager")){
            view.  showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Insurance_Surveyor")){
            view.  showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("System_Admin")){
            view.  showPolicyHolderInformation(selectedCustomer);
        } else if (selectedCustomer.getRole().name().equals("Policy_Owner")){
            view.  showPolicyHolderInformation(selectedCustomer);
        }
    }
}