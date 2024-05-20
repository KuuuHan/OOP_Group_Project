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
import ui.gp.Models.Users.*;
import ui.gp.SceneController.Controllers.ManagerController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.Tab.ClaimController;

import java.io.IOException;
import java.sql.PreparedStatement;
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
    private TableColumn insuredPeopleManagerView;

    @FXML
    private TextField managerAddress;

    @FXML
    private Button managerApprovalButton;

    @FXML
    private ComboBox<?> managerClaimFilter;

    @FXML
    private ComboBox<String> managerCustomerFilter;

    @FXML
    private Button managerDeclineButton;

    @FXML
    private TextField managerEmail;

    @FXML
    private ComboBox<?> managerFilterSurveyor;

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
    private Tab managerViewClaimTab;

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
    private Label welcomeBannerUser;
    @FXML
    private TableView<Customer> customerManagerTable;
    @FXML
    private TableView<Provider> surveyorManagerTable;
    @FXML
    TableColumn idClaimPolicyOwnerTable;
    @FXML
    TableColumn datePoilicyOwnerTable;
    @FXML
    TableColumn insuredPersonPoilicyOwnerTable;
    @FXML
    TableColumn examDatePoilicyOwnerTable;
    @FXML
    TableColumn documentsPolicyOwnerTable;
    @FXML
    TableColumn claimAmountPoilicyOwnerTable;
    @FXML
    TableColumn statusPoilicyOwnerTable;
    @FXML
    TableColumn bankingInfoPoilicyOwnerTable;

    @FXML
    private TableView<Claim> claimManagerTable;
    private Manager manager;
    private ManagerController managerController;
    private String search;
    private ClaimController claimController;
    private final   ObservableList<Customer> dataList = FXCollections.observableArrayList();


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username);
    }

    public void initialize(Manager manager, ManagerController managerController) {
        this.manager = manager;
        this.managerController = managerController;
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

//        managerViewClaimTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                populateClaimTable();
//            }
//        });
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
//            populateCustomerTable();
//            populateSurveyorTable();
//            populateClaimTable();
//        }));


//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
    }




    @FXML
    public void handleProfileTabSelection() {
        if (managerInfoTab.isSelected()) {
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

        idClaimPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        datePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("claim_date"));
        insuredPersonPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("insured_person"));
        examDatePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("exam_date"));
        documentsPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        claimAmountPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("claim_amount"));
        statusPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("claim_status"));
        bankingInfoPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>(""));

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
        customerRoleManagerView.setCellValueFactory(new PropertyValueFactory<>("insured_person"));
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

        List<Provider> surveyors = managerController.retrieveSurveyor();
        ObservableList<Provider> data = FXCollections.observableArrayList(surveyors);

        surveyorIDManagerView.setCellValueFactory(new PropertyValueFactory<>("id"));
        surveyorNameManagerView.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        surveyorPhoneManagerView.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        surveyorEmailManagerView.setCellValueFactory(new PropertyValueFactory<>("email"));
        surveyorAddressManagerView.setCellValueFactory(new PropertyValueFactory<>("address"));

        surveyorManagerTable.setItems(data);
    }

//    public void populateClaimTable() {
//
//        List<Claim> claims = managerController.retrieveClaims();
//        ObservableList<Claim> data = FXCollections.observableArrayList(claims);
//
//        claimIDManagerView.setCellValueFactory(new PropertyValueFactory<>("claimId"));
//        insuredPeopleManagerView.setCellValueFactory(new PropertyValueFactory<>("holderId"));
//        claimAmountManagerView.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
//        statusManagerView.setCellValueFactory(new PropertyValueFactory<>("status"));
//        claimDateManagerView.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
//        examDateManagerView.setCellValueFactory(new PropertyValueFactory<>("examDate"));
//
//        claimManagerTable.setItems(data);
//    }

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


    public void processClaim(ActionEvent event) throws IOException {
        claimController.processManagerClaim(event);
    }
}