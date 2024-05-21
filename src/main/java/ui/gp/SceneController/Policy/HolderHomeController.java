package ui.gp.SceneController.Policy;

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
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.InsuranceCard;
import ui.gp.Models.ReceiverBankingInfo;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyHolder;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.SceneController.Controllers.PolicyHolderController;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.SceneUtil;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HolderHomeController {
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane policyHolderHomeScene;
    @FXML
    Tab infoTab;
    @FXML
    TextField idFieldInfo;
    @FXML
    TextField fullnameFieldInfo;
    @FXML
    TextField usernameFieldInfo;
    @FXML
    TextField passwordFieldInfo;
    @FXML
    TextField emailFiieldInfo;
    @FXML
    TextField phonenumberFieldInfo;
    @FXML
    TextField addressFieldInfo;
    @FXML
    private BorderPane ViewClaimPane;

    @FXML
    private BorderPane ViewClaimPane1;

    @FXML
    private TableColumn actionNameID;

    @FXML
    private Tab hIstoryRecord;

    @FXML
    private TableView<?> historyRecordID;

    @FXML
    private TableColumn holderClaimAmount;

    @FXML
    private TableColumn holderClaimDate;

    @FXML
    private Button holderClaimEdit;

    @FXML
    private ComboBox<String> holderClaimFilter;

    @FXML
    private TableColumn holderClaimId;

    @FXML
    private TextField holderClaimSearch;

    @FXML
    private TableView holderClaimTable;

    @FXML
    private TableColumn holderDependentAddress;

    @FXML
    private Button holderDependentEdit;

    @FXML
    private TableColumn holderDependentEmail;

    @FXML
    private TableColumn holderDependentId;

    @FXML
    private TableColumn holderDependentName;

    @FXML
    private TableColumn holderDependentPhone;

    @FXML
    private TextField holderDependentSearch;

    @FXML
    private TableView holderDependentTable;

    @FXML
    private TableColumn holderExamDate;

    @FXML
    private Tab holderFileClaimTab;

    @FXML
    private TableColumn holderInsuredPerson;

    @FXML
    private Tab holderProfileTab;

    @FXML
    private TableColumn holderStatus;

    @FXML
    private Tab holderViewClaimTab;

    @FXML
    private Tab holderDependentTab;


    @FXML
    private Button resetButton;

    @FXML
    private Button saveButton;
    @FXML
    private Button holderFileSubmit;
    @FXML
    private TextField holderFileClaimId;
    @FXML
    private TextField holderFileUserId;
    @FXML
    private TextField holderFileName;
    @FXML
    private TextField holderFileInsuranceNumber;
    @FXML
    private TextField holderFileInsuranceOwner;
    @FXML
    private TextField holderFileInsuranceDate;
    @FXML
    private TextField holderFileUser;
    @FXML
    private TextField holderFilePassword;
    @FXML
    private TextField holderFileAmount;
    @FXML
    private TextField holderFileBankNumber;
    @FXML
    private TextField holderFileBank;
    @FXML
    private TextField holderFileEmail;

    private PolicyHolder policyHolder;
    private PolicyHolderController policyHolderController;
    private String holderName;



    public void initialize(PolicyHolder policyHolder, PolicyHolderController policyHolderController) {
        bannerNameView(policyHolder.getFullname());
        this.policyHolder = policyHolder;
        this.policyHolderController = policyHolderController;
        if (holderProfileTab.isSelected()) {
            handleProfileTabSelection();
        }
        holderDependentTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populateHolderTable();
                holderDependentEdit.setDisable(true);
            }
        });

//        policyOwnerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                selectedBeneficiary = (Customer) newSelection;
//                deleteBeneficiaryButton.setDisable(false);
//                showInfoBeneficiaryButton.setDisable(false);
//                updateBeneficiaryButton.setDisable(false);
//            } else {
//                deleteBeneficiaryButton.setDisable(true);
//                showInfoBeneficiaryButton.setDisable(true);
//                updateBeneficiaryButton.setDisable(true);
//            }
//        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
//            populatePolicyOwnerTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

//        List<String> filterList = new ArrayList<>();
//        filterList.add("All");
//        filterList.add("Policy Holder");
//        filterList.add("Dependent");
//        filterBeneficiaryBox.setItems(FXCollections.observableArrayList(filterList));
//        filterBeneficiaryBox.setValue(filterList.get(0));

    }


    public void handleProfileTabSelection() {
        if (holderProfileTab.isSelected() && policyHolderController != null) {
            String[] information = policyHolderController.retrieveInformation().split("\n");
            idFieldInfo.setText(information[0].split(": ")[1]);
            fullnameFieldInfo.setText(information[1].split(": ")[1]);
            usernameFieldInfo.setText(information[2].split(": ")[1]);
            passwordFieldInfo.setText(information[3].split(": ")[1]);
            emailFiieldInfo.setText(information[4].split(": ")[1]);
            phonenumberFieldInfo.setText(information[5].split(": ")[1]);
            addressFieldInfo.setText(information[6].split(": ")[1]);
        }
    }


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username + "!");
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(policyHolderHomeScene);
        System.out.println("Policy Holder logout");
    }

    public void populateHolderTable() {

        List<Customer> beneficiaries = policyHolderController.retrieveBeneficiaries();
        ObservableList<Customer> dataList = FXCollections.observableArrayList(beneficiaries);

        holderDependentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        holderDependentName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        holderDependentPhone.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        holderDependentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        holderDependentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);

        holderDependentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(benefeciaries -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (benefeciaries.getFullname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (benefeciaries.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(holderDependentTable.comparatorProperty());
        holderDependentTable.setItems(sortedData);
    }
//    @FXML
//    public void claimSubmitButton(ActionEvent event)
//    {
//        boolean userCheck;
//        List<Customer> customers = policyHolderController.retrieveBeneficiaries();
//        List<InsuranceCard> insuranceCards = policyHolderController.retrieveInsurance();
//        List<ReceiverBankingInfo> bankingInfos = policyHolderController.retrieveBank();
//        String claimId = holderFileClaimId.getText();
//        String userId = holderFileUserId.getText();
//        for (Customer customer: customers) {
//            if (userId == customer.getId())
//            {
//                userCheck = true;
//                holderFileName.setText(customer.getFullname().toString());
//                holderFileEmail.setText(customer.getEmail().toString());
//                InsuranceCard holderInsurance = getInsuranceCardFromId(insuranceCards, customer.getFullname());
//                ReceiverBankingInfo bankInfo = getBankFromId(bankingInfos, customer.getFullname());
//                holderFileInsuranceNumber.setText(holderInsurance.getCardNumber().toString());
//                holderFileInsuranceOwner.setText(holderInsurance.getPolicyOwner().toString());
//                holderFileInsuranceDate.setText(holderInsurance.getExpirationDate().toString());
//                holderFileUser.setText(customer.getUsername().toString());
//                holderFilePassword.setText(customer.getPassword().toString());
//                String claimAmountString = holderFileAmount.getText();
//                double claimAmount = Double.parseDouble(claimAmountString);
//                holderFileBankNumber.setText(bankInfo.getNumber().toString());
//                holderFileBank.setText(bankInfo.getBankName().toString());
//
//            }
//        }
//        String phoneNumber = phonenumberFieldInfo.getText();
//        String address = addressFieldInfo.getText();
//        String username = usernameFieldInfo.getText();
//
//        //Save update data to the database
//        updateProfile(password, email, phoneNumber, address, username);
//
//    }

    private void createClaim(String password, String email, String phoneNumber, String address,String username)
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
    public void onProfileSaveButton(ActionEvent event){

        String password = passwordFieldInfo.getText();
        String email = emailFiieldInfo.getText();
        String phoneNumber = phonenumberFieldInfo.getText();
        String address = addressFieldInfo.getText();
        String username = usernameFieldInfo.getText();

        //Save update data to the database
        updateProfile(password, email, phoneNumber, address, username);

    }

    @FXML
    public void onProfileResetButton(ActionEvent event){
        String[] information = policyHolderController.retrieveInformation().split("\n");
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
                emailFiieldInfo.setText(resultSet.getString("email"));
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
    public InsuranceCard getInsuranceCardFromId(List<InsuranceCard> insuranceCards, String holderName) {
        for (InsuranceCard insuranceCard : insuranceCards) {
            if (insuranceCard.getCardHolder().equals(holderName)) {
                return insuranceCard;
            }
        }
        return null;
    }

    public ReceiverBankingInfo getBankFromId(List<ReceiverBankingInfo> bankInfos, String holderName) {
        for (ReceiverBankingInfo bankingInfo : bankInfos) {
            if (bankingInfo.getOwnerName().equals(holderName)) {
                return bankingInfo;
            }
        }
        return null;
    }
}
