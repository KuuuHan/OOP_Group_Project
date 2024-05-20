package ui.gp.SceneController.Manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
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
import java.util.ArrayList;
import java.util.List;

public class SurveyorHomeController {
    @FXML
    private TableColumn claimAmountPendingSurveyorView;

    @FXML
    private TableColumn claimAmountSurveyorView;

    @FXML
    private TableColumn claimDatePendingSurveyorView;

    @FXML
    private TableColumn claimDateSurveyorView;

    @FXML
    private TableColumn claimIDSurveyorView;

    @FXML
    private TableView claimSurveyorTable;

    @FXML
    private TableView claimPendingSurveyorTable;

    @FXML
    private TableColumn customerAddressSurveyorView;

    @FXML
    private TableColumn customerEmailSurveyorView;

    @FXML
    private TableColumn customerIDSurveyorView;

    @FXML
    private TableView customerSurveyorTable;

    @FXML
    private TableColumn customerNameSurveyorView;

    @FXML
    private TableColumn customerPhoneSurveyorView;

    @FXML
    private TableColumn customerRoleSurveyorView;

    @FXML
    private TableColumn examDatePendingSurveyorView;

    @FXML
    private TableColumn examDateSurveyorView;

    @FXML
    private TableColumn insuredPeoplePendingSurveyorView;

    @FXML
    private TableColumn insuredPeopleSurveyorView;

    @FXML
    private TableColumn statusPendingSurveyorView;

    @FXML
    private TableColumn statusSurveyorView;

    @FXML
    private Button surveyorApprovalButton;

    @FXML
    private ComboBox<?> surveyorClaimFilter;

    @FXML
    private ComboBox<?> surveyorCustomerFilter;

    @FXML
    private AnchorPane surveyorHomeScene;

    @FXML
    private TableColumn claimIdPendingSurveyorView;

    @FXML
    private Tab surveyorInfoTab;

    @FXML
    private TextField surveyorSearchClaim;

    @FXML
    private TextField surveyorSearchCustomer;

    @FXML
    private TextField surveyorSearchPendingClaim;

    @FXML
    private Button surveyorViewClaim;

    @FXML
    private Tab surveyorViewClaimTab;

    @FXML
    private Button surveyorViewCustomer;

    @FXML
    private Tab surveyorViewCustomerTab;

    @FXML
    private Button surveyorViewPendingClaim;

    @FXML
    private Tab surveyorViewPendingClaimTab;

    @FXML
    private Label welcomeBannerUser;
    @FXML
    private AnchorPane managerHomeScene;

    @FXML
    private TextField surveyorID;
    @FXML
    private TextField surveyorName;
    @FXML
    private TextField surveyorUser;
    @FXML
    private TextField surveyorPassword;
    @FXML
    private TextField surveyorEmail;
    @FXML
    private TextField surveyorPhone;
    @FXML
    private TextField surveyorAddress;


    private DatabaseConnection databaseConnection;
    private ViewFactory view;
    private User selectedCustomer;
    private Claim selectedClaim;
    private InsuranceSurveyor surveyor;
    private SurveyorController surveyorController;


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void initialize(InsuranceSurveyor surveyor, SurveyorController surveyorController) {
        this.surveyor = surveyor;
        this.surveyorController = surveyorController;
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
        if (surveyorInfoTab.isSelected()) {
            handleSurveyorProfileTabSelection();
        }
        surveyorViewCustomerTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateCustomerTable();
            }
        });

//        List<String> displayComboList = new ArrayList<>();
//        displayComboList.add("All");
//        displayComboList.add("Policy Holder");
//        displayComboList.add("Dependent");
//        surveyorCustomerFilter.setItems(FXCollections.observableArrayList(displayComboList));
//        surveyorCustomerFilter.setValue(displayComboList.get(0));

        surveyorViewClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateClaimTable();
            }

        });
        surveyorViewPendingClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populatePendingClaimTable();
            }

        });

//        List<String> displayClaimComboList = new ArrayList<>();
//        displayClaimComboList.add("All");
//        displayClaimComboList.add("Rejected");
//        displayClaimComboList.add("Approved");
//        displayClaimComboList.add("Pending");
//        surveyorClaimFilter.setItems(FXCollections.observableArrayList(displayClaimComboList));
//        surveyorClaimFilter.setValue(displayClaimComboList.get(0));

        claimPendingSurveyorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClaim = (Claim) newSelection;
                surveyorApprovalButton.setDisable(false);
                surveyorViewClaim.setDisable(false);
            } else {
                surveyorApprovalButton.setDisable(false);
                surveyorViewClaim.setDisable(false);
            }
        });
    }

    @FXML
    public void handleSurveyorProfileTabSelection() {
        if (surveyorInfoTab.isSelected() && surveyorController != null) {
            String[] information = surveyorController.retrieveInformation().split("\n");
            surveyorID.setText(information[0].split(": ")[1]);
            surveyorName.setText(information[1].split(": ")[1]);
            surveyorUser.setText(information[2].split(": ")[1]);
            surveyorPassword.setText(information[3].split(": ")[1]);
            surveyorEmail.setText(information[4].split(": ")[1]);
            surveyorPhone.setText(information[5].split(": ")[1]);
            surveyorAddress.setText(information[6].split(": ")[1]);
        }
    }

    public void populateCustomerTable() {

        List<Customer> beneficiaries = surveyorController.retrieveBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(beneficiaries);

        customerIDSurveyorView.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameSurveyorView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        customerRoleSurveyorView.setCellValueFactory(new PropertyValueFactory<>("role"));
        customerPhoneSurveyorView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customerEmailSurveyorView.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressSurveyorView.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(customerSurveyorTable.comparatorProperty());
        customerSurveyorTable.setItems(sortedData);
    }

    public void populateClaimTable() {

        List<Claim> claims = surveyorController.retrieveClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIDSurveyorView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeopleSurveyorView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountSurveyorView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusSurveyorView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDateSurveyorView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateSurveyorView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPerson().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(claimSurveyorTable.comparatorProperty());
        claimSurveyorTable.setItems(sortedData);
    }

    public void populatePendingClaimTable() {

        List<Claim> claims = surveyorController.retrievePendingClaims();
        ObservableList<Claim> dataList = FXCollections.observableArrayList(claims);

        claimIdPendingSurveyorView.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPeoplePendingSurveyorView.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        claimAmountPendingSurveyorView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusSurveyorView.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimDatePendingSurveyorView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDatePendingSurveyorView.setCellValueFactory(new PropertyValueFactory<>("examDate"));

        FilteredList<Claim> filteredData = new FilteredList<>(dataList, b -> true);

        surveyorSearchPendingClaim.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (claim.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (claim.getInsuredPerson().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(claimPendingSurveyorTable.comparatorProperty());
        claimPendingSurveyorTable.setItems(sortedData);
    }

    @FXML
    public void SurveyorLogout(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(managerHomeScene);
        System.out.println("Insurance Surveyor logout");
    }
}


