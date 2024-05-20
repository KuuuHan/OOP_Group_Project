package ui.gp.Tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.*;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.SceneController.Function.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClaimController {
    @FXML
    private TextField idFieldClaim;
    @FXML
    private ChoiceBox<String> ClaimBeneficieryBox;
    private ChoiceBox<String> PolicyOwnerChoiceBox;
    private PolicyOwnerController policyOwnerController;
    @FXML
    private TextField insuredPersonFieldClaim;
    @FXML
    private TextField cardNumberFieldClaim;
    @FXML
    private TextField claimDateFieldClaim;
    @FXML
    private TextField claimAmountFieldClaim;
    @FXML
    private TextField statusFieldClaim;
    @FXML
    private TextField bankCardNumberFieldClaim;
    @FXML
    private TextField cardHolderFieldClaim;

    @FXML
    private TextField expirationDateFieldClaim;
    @FXML
    private TextField bankNameFieldClaim;
    @FXML
    private TextField bankCardOwnerFieldClaim;
    @FXML
    private Button uploadButton;
    @FXML
    private ComboBox<String> fileNameComboBox;
    private PolicyOwner policyOwner;
    private List<Customer> beneficiariesList;
    private DatabaseConnection databaseConnection;
    private UploadController uploadController;


    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    public ClaimController() {
        uploadController = new UploadController();

    }
    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    @FXML
    public void onSubmit() {
        String claimDate = claimDateFieldClaim.getText();
        String insuredPerson = ClaimBeneficieryBox.getValue();
        String cardNumber = cardNumberFieldClaim.getText();
        String expirationDate = expirationDateFieldClaim.getText();
        String claimAmount = claimAmountFieldClaim.getText();
        String bankName = bankNameFieldClaim.getText();
        String bankCardNumber = bankCardNumberFieldClaim.getText();

        String[] parts = insuredPerson.split("\\.");
        String insuredPersonId = parts[0].trim(); // this will contain the part before "-"

        String claimID = addtoclaim(claimDate, insuredPersonId, cardNumber, expirationDate, claimAmount, bankName, bankCardNumber);
        List<File> selectedFiles = uploadController.getSelectedFiles();
        // You can use claimID for further processing if needed
        for (File file : selectedFiles) {
            // For each file, insert the file name and claim ID into the database
            InsertDocuments(claimID, file.getName());
            Path sourcePath = file.toPath();
            Path targetPath = Paths.get("src/main/resources/ui/gp/Documents", file.getName());
            try {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Copy the file to the Documents directory in ui.gp in resources


    }
    public String addtoclaim(String claimDate, String insuredpersonid, String cardNumber, String ExpirationDate, String Amount, String bankName, String bankNumber) {
        String id = null;
        try {
            double claimvalue = Double.parseDouble(Amount); // Convert Amount to double
            java.sql.Date sqlClaimDate = java.sql.Date.valueOf(claimDate);

            java.sql.Date sqlExpDate = java.sql.Date.valueOf(ExpirationDate);
            String query = "INSERT INTO claim (claim_date, insured_person, claim_amount, card_number_bank, bank_name, card_number_insurance, expiration_date_insurance, card_owner_bank, card_holder_insurance,policy_owner_insurance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, sqlClaimDate);
            statement.setString(2, insuredpersonid);
            statement.setDouble(3, Double.parseDouble(Amount));
            statement.setString(4, bankNumber);
            statement.setString(5, bankName);
            statement.setString(6, cardNumber);
            statement.setDate(7, sqlExpDate);
            String FullName = getFullName(insuredpersonid);
            statement.setString(8, FullName);
            statement.setString(9, FullName);
            User currentUser = Session.getInstance().getUser();
            String currentUsername = currentUser.getUsername();
            String currentPassword = currentUser.getPassword();
            PreparedStatement userStatement = DatabaseConnection.getInstance().getConnection().prepareStatement("SELECT id FROM Users WHERE username = ? AND password = ?");
            // policy owner
            userStatement.setString(1, currentUsername);
            userStatement.setString(2, currentPassword);
            ResultSet rs = userStatement.executeQuery();
            String userId = null;
            if (rs.next()) {
                userId = rs.getString("id");
            }
            statement.setString(10, userId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating claim failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getString(1);
                } else {
                    throw new SQLException("Creating claim failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //    public List<String> getPolicyHolderIDsLinkedWithOwner(String policyOwnerID) {
//        List<String> policyHolders = new ArrayList<>();
//        try {
//            String query = "SELECT policyowner.policyholderid, Users.fullname FROM policyowner JOIN Users ON policyowner.policyholderid = Users.id WHERE policyownerid = ?";
//            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
//            statement.setString(1, policyOwnerID);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                String id = rs.getString("policyholderid");
//                String name = rs.getString("fullname");
//                String tmp = id + " - " + name;
//                policyHolders.add(tmp);
//                System.out.println(id + " - " + name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return policyHolders;
//    }
    public void setBeneficiariesList(List<Customer> beneficiariesList) {
        this.beneficiariesList = beneficiariesList;
        ObservableList<String> Beneficiaries = FXCollections.observableArrayList();
        for (Customer beneficiary : beneficiariesList) {
            Beneficiaries.add(beneficiary.getId() + ". " + beneficiary.getFullname());
        }
        ClaimBeneficieryBox.setItems(Beneficiaries);
    }


    public String getFullName(String insuredPersonId) {
        String fullName = null;
        try {
            String query = "SELECT fullname FROM Users WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, insuredPersonId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                fullName = rs.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    //List of documents
    public void updateFileNameComboBox(String newFileName) {
        fileNameComboBox.getItems().add(newFileName);
    }
    public void InsertDocuments(String Claimid, String FILENAME) {
        try {
            String query = "INSERT INTO Documents (claimid, documentname) VALUES (?, ?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, Claimid);
            statement.setString(2, FILENAME);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openUploadWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ProofUploadScene.fxml"));
        uploadController.setClaimController(this); // set the ClaimController
        loader.setController(uploadController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void NotConfirmClaim(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void processClaim(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ClaimProcessor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void processManagerClaim(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ClaimProcessor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public void deleteClaim(String id) {
        try {
            String query = "DELETE FROM claimtable WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}