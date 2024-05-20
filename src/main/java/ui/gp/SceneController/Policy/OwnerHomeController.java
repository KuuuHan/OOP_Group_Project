package ui.gp.SceneController.Policy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ui.gp.Models.Claim;
import ui.gp.Models.Model;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.SceneController.Function.SceneUtil;
import ui.gp.View.ViewFactory;
import ui.gp.Database.DatabaseConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerHomeController {
    
    public Tab BeneficiaryTab;
    public TableView policyOwnerTable;
    public TableColumn rolePoilicyOwnerTable;
    public TableView policyOwnerClaimTable;
    public TableColumn idClaimPolicyOwnerTable;
    public TableColumn datePolicyOwnerTable;
    public TableColumn insuredPersonPolicyOwnerTable;
    public TableColumn expiredDate;
    public TableColumn claimAmountPolicyOwnerTable;
    public TableColumn cardNumInsuranceOwnerTable;
    public TableColumn idPolicyOwnerTable;
    public TableColumn fullnamePolicyOwnerTable;
    public TableColumn usernamePoilicyOwnerTable;
    public TableColumn passwordPoilicyOwnerTable;
    public TableColumn addressPoilicyOwnerTable;
    public TableColumn emailPoilicyOwnerTable;
    public TableColumn phonenumberPoilicyOwnerTable;
    public Button addClaimPoilicyOwnerButton;
    public Button DeletePolicyClaimbutton;
    public Button showClaimPoilicyOwnerButton;
    public Button updateClaimPoilicyOwnerButton;
    private PolicyOwnerController policyOwnerController;
    private DatabaseConnection databaseConnection;
    public TextField idFieldInfo;
    public TextField fullnameFieldInfo;
    public TextField usernameFieldInfo;
    public TextField passwordFieldInfo;
    public TextField phonenumberFieldInfo;
    public TextField addressFieldInfo;
    public TextField emailFiieldInfo;
    public Button addPolicyHolderButton;
    public Button addDependentButton;
    public Button deleteBeneficiaryButton;
    public Button updateBeneficiaryButton;
    public Button showInfoBeneficiaryButton;
    public ComboBox <String> filterBeneficiaryBox;
    private User selectedBeneficiary;
    public Tab infoTab;
    public Tab ClaimManageTab;
    private PolicyOwner policyOwner;
    private ObservableList<User> items;
    private TableView<User> ownerHomeTable;
    @FXML
    Label welcomeBannerUser;
    @FXML
    AnchorPane ownerHomeScene;
    @FXML
    Button logoutButton;
    ViewFactory view;
    private ObservableList<Customer> masterData = FXCollections.observableArrayList();

    public OwnerHomeController() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(Model.getInstance().getDatabaseConnection());
    }


    public void initialize(PolicyOwner policyOwner, PolicyOwnerController policyOwnerController) {
        bannerNameView(policyOwner.getFullname());
        this.policyOwner = policyOwner;
        this.policyOwnerController = policyOwnerController;
        if (infoTab.isSelected()) {
            handleProfileTabSelection();
        }
        BeneficiaryTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populatePolicyOwnerTable();
                deleteBeneficiaryButton.setDisable(true);
                showInfoBeneficiaryButton.setDisable(true);
                updateBeneficiaryButton.setDisable(true);
            }
        });
        ClaimManageTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                populatePolicyOwnerClaimTable();
            }
        });
        policyOwnerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBeneficiary = (User) newSelection;
                deleteBeneficiaryButton.setDisable(false);
                showInfoBeneficiaryButton.setDisable(false);
                showInfoBeneficiaryButton.setDisable(false);
                updateBeneficiaryButton.setDisable(false);
            } else {
                deleteBeneficiaryButton.setDisable(true);
                showInfoBeneficiaryButton.setDisable(true);
                updateBeneficiaryButton.setDisable(true);
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            populatePolicyOwnerTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        List<String> filterList = new ArrayList<>();
        filterList.add("All");
        filterList.add("Policy Holder");
        filterList.add("Dependent");
        filterBeneficiaryBox.setItems(FXCollections.observableArrayList(filterList));
        filterBeneficiaryBox.setValue(filterList.get(0));

    }

    @FXML
    public void updateBeneficiaryButtonAction() {
        if (selectedBeneficiary != null) {
            policyOwnerTable.getSelectionModel().clearSelection();
            deleteBeneficiaryButton.setDisable(true);
            showInfoBeneficiaryButton.setDisable(true);
            updateBeneficiaryButton.setDisable(true);
            if (selectedBeneficiary.getRole().name().equals("Dependent")) {
                view.showDepenentFormUpdate(selectedBeneficiary);
            } else {
                view.showPolicyHolderFormUpdate(selectedBeneficiary);
            }
        }
    }

    @FXML
    public void showBeneficiaryButtonAction() {
        if (selectedBeneficiary != null) {
            policyOwnerTable.getSelectionModel().clearSelection();
            deleteBeneficiaryButton.setDisable(true);
            showInfoBeneficiaryButton.setDisable(true);
            updateBeneficiaryButton.setDisable(true);
            if (selectedBeneficiary.getRole().name().equals("Dependent")) {
                view.showDependentInformation(selectedBeneficiary);
            } else {
                view.showPolicyHolderInformation(selectedBeneficiary);
            }
        }
    }

    public void DeleteBeneficiaryButton()
    {
        if (selectedBeneficiary != null) {
            policyOwnerTable.getSelectionModel().clearSelection();
            deleteBeneficiaryButton.setDisable(true);
            showInfoBeneficiaryButton.setDisable(true);
            updateBeneficiaryButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Your Deletion");
            alert.setHeaderText("Delete Beneficiary");
            alert.setContentText("Are you sure you want to delete this beneficiary?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                deleteBeneficiary(selectedBeneficiary);
                populatePolicyOwnerTable();
            }
        }
    }

    private void deleteBeneficiary(User selectedBeneficiary) {
        try {
            Thread deleteThread = new Thread(() -> {
                try {
                    String userQuery = "DELETE FROM Users WHERE id = ?";
                    try (PreparedStatement userStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(userQuery)) {
                        userStatement.setString(1, selectedBeneficiary.getId());
                        userStatement.executeUpdate();

                    }

                    if (selectedBeneficiary.getRole().name().equals("Dependent")) {
                        String dependentQuery = "DELETE FROM policyholder WHERE dependentid = ?";
                        try (PreparedStatement dependentStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(dependentQuery)) {
                            dependentStatement.setString(1, selectedBeneficiary.getId());
                            dependentStatement.executeUpdate();
                        }
                    } else {
                        String dependentQuery2 = "DELETE FROM Users WHERE id IN (SELECT dependentid FROM policyholder WHERE policyholderid = ?)";
                        try (PreparedStatement dependentStatement2 = DatabaseConnection.getInstance().getConnection().prepareStatement(dependentQuery2)) {
                            dependentStatement2.setString(1, selectedBeneficiary.getId());
                            dependentStatement2.executeUpdate();
                        }
                        String policyholderQuery = "DELETE FROM policyowner WHERE policyholderid = ?";
                        try (PreparedStatement policyholderStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(policyholderQuery)) {
                            policyholderStatement.setString(1, selectedBeneficiary.getId());
                            policyholderStatement.executeUpdate();
                        }

                        String dependentQuery = "DELETE FROM policyholder WHERE policyholderid = ?";
                        try (PreparedStatement dependentStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(dependentQuery)) {
                            dependentStatement.setString(1, selectedBeneficiary.getId());
                            dependentStatement.executeUpdate();
                        }
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


    public void bannerNameView(String username) {
        welcomeBannerUser.setText("Welcome " + username + "!");
    }

    @FXML
    public void logoutOwner(ActionEvent logoutAction) throws IOException {
        SceneUtil.logout(ownerHomeScene);
        System.out.println("Policy Owner logout");
    }

    public void AddPolicyHolderButton() throws IOException {
        view.showPolicyHolderForm();
    }


    public void AddDependentButton() throws IOException {
        ViewFactory view = new ViewFactory(databaseConnection);
        view.showDependentForm();
    }



    @FXML
    public void handleProfileTabSelection() {
        if (infoTab.isSelected() && policyOwnerController != null) {
            String[] information = policyOwnerController.retrieveInformation().split("\n");
            idFieldInfo.setText(information[0].split(": ")[1]);
            fullnameFieldInfo.setText(information[1].split(": ")[1]);
            usernameFieldInfo.setText(information[2].split(": ")[1]);
            passwordFieldInfo.setText(information[3].split(": ")[1]);
            emailFiieldInfo.setText(information[4].split(": ")[1]);
            phonenumberFieldInfo.setText(information[5].split(": ")[1]);
            addressFieldInfo.setText(information[6].split(": ")[1]);
        }
    }

    public void populatePolicyOwnerTable() {

        List<Customer> beneficiaries = policyOwnerController.retrieveBeneficiaries();
        ObservableList<Customer> data = FXCollections.observableArrayList(beneficiaries);

        idPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnamePolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        usernamePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumberPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        passwordPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressPoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("address"));
        rolePoilicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("role"));

        policyOwnerTable.setItems(data);
        if (selectedBeneficiary != null)
        {
            policyOwnerTable.getSelectionModel().select(selectedBeneficiary);
        }
    }



    @FXML
    public void onFilterBox(ActionEvent event) {
        String filter = filterBeneficiaryBox.getSelectionModel().getSelectedItem();
        if (filter != null) {
            if (filter.equals("All")) {
                policyOwnerTable.setItems(FXCollections.observableArrayList(policyOwnerController.retrieveBeneficiaries()));
            } else {
                ObservableList<Customer> filteredData = FXCollections.observableArrayList();
                for (Customer customer : policyOwnerController.retrieveBeneficiaries()) {
                    if (customer.getRole().name().equals(filter.replace(" ", "_"))) {
                        filteredData.add(customer);
                    }
                }
                policyOwnerTable.setItems(filteredData);
            }
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
        String[] information = policyOwnerController.retrieveInformation().split("\n");
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

    public void populatePolicyOwnerClaimTable() {

        List<Claim> Claim = policyOwnerController.retrieveAllClaims();
        ObservableList<Claim> data = FXCollections.observableArrayList(Claim);

        idClaimPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        datePolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("date"));
        insuredPersonPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("insuredPersonID"));
        expiredDate.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        claimAmountPolicyOwnerTable.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        cardNumInsuranceOwnerTable.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));

        policyOwnerClaimTable.setItems(data);
        if (selectedBeneficiary != null)
        {
            policyOwnerClaimTable.getSelectionModel().select(selectedBeneficiary);
        }
    }


    @FXML
    public void addItemOnClick( ) throws IOException {
        ViewFactory view = new ViewFactory(databaseConnection);
        view.showClaimForm(policyOwnerController.retrieveBeneficiaries(), policyOwner);

    }

    @FXML
    public void deleteClaimButtonAction() {
        // Get the selected claim from the table
        Object selectedClaim = policyOwnerClaimTable.getSelectionModel().getSelectedItem();

        // Check if a row has been selected
        if (selectedClaim != null) {
            // Cast selectedClaim to Claim type and delete the claim from the database
            deleteClaimFromDatabase(((Claim) selectedClaim).getId());

            // Refresh the table
            populatePolicyOwnerClaimTable();
        } else {
            // Show an error message if no row has been selected
            showErrorDialog("Please select a claim to delete.");
        }
    }

    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setShowSpecificClaimAction(){
        //Get the selected claim from the table
        Object selectedClaim = policyOwnerClaimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            // Cast selectedClaim to Claim type and delete the claim from the database
            Claim claim = PolicyOwnerController.retrieveclaimsforid(((Claim) selectedClaim).getId());
            List<String> documentNames = retrievelistofimage(claim.getId());



        } else {
            // Show an error message if no row has been selected
            showErrorDialog("Please select a claim to show.");
        }

    }

    private List<String> retrievelistofimage(String claimId) {
        String query = "SELECT documentname FROM claim WHERE id = ?";
        List<String> documentNames = new ArrayList<>();
        try {
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, claimId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                documentNames.add(resultSet.getString("documentname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }
        return documentNames;
    }

    private void deleteClaimFromDatabase(String claimId) {
        try {
            String query = "DELETE FROM claim WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, claimId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Claim with ID: " + claimId + " deleted successfully from the database.");
            } else {
                System.out.println("No claim found with ID: " + claimId + " in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
