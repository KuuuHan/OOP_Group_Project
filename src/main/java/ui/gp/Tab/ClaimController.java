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

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClaimController
{
    @FXML
    private TextField idFieldClaim;
    @FXML
    private ChoiceBox<String> ClaimBeneficieryBox;

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
    private TextField policyOwnerFieldClaim;
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

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
    @FXML
    public void onSubmit() {
        String claimDate = claimDateFieldClaim.getText();
        String insuredPerson = ClaimBeneficieryBox.getValue();
        String cardNumber = cardNumberFieldClaim.getText();
        String cardHolder = cardHolderFieldClaim.getText();
        String policyOwner = policyOwnerFieldClaim.getText();
        String expirationDate = expirationDateFieldClaim.getText();
        String claimAmount = claimAmountFieldClaim.getText();
        String bankName = bankNameFieldClaim.getText();
        String bankCardOwner = bankCardOwnerFieldClaim.getText();
        String bankCardNumber = bankCardNumberFieldClaim.getText();
        String[] parts = insuredPerson.split("\\.");
        String insuredPersonId = parts[0].trim(); // this will contain the part before "-"

        String claimID = addtoclaim(claimDate, insuredPersonId, cardNumber, cardHolder, policyOwner, expirationDate, claimAmount, bankName, bankCardOwner, bankCardNumber);

        // You can use claimID for further processing if needed
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
public void setBeneficiariesList(List<Customer> beneficiariesList)
{
    this.beneficiariesList = beneficiariesList;
    ObservableList<String> Beneficiaries = FXCollections.observableArrayList();
    for (Customer beneficiary : beneficiariesList) {
        Beneficiaries.add(beneficiary.getId() + ". " + beneficiary.getFullname());
    }
    ClaimBeneficieryBox.setItems(Beneficiaries);
}

    public String addtoclaim(String claimDate, String insuredpersonid, String cardNumber, String cardHolderid, String PolicyOwnerid, String ExpirationDate, String Amount, String bankName , String bankCardOwner, String bankNumber) {
        String id = null;
        try {
            double claimvalue = Double.parseDouble(Amount); // Convert Amount to double
            String query = "INSERT INTO claimTable (claimDate, insuredPerson, cardNumber, cardHolder, expirationDate, claimAmount, bankName, bankCardOwner, bankCardNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, claimDate);
            statement.setString(2, insuredpersonid);
            statement.setString(3, cardNumber);
            statement.setString(4, cardHolderid);
            statement.setString(5, cardHolderid);
            statement.setString(5, ExpirationDate);
            statement.setDouble(6, claimvalue);
            statement.setString(7, bankName);
            statement.setString(8, bankCardOwner);
            statement.setString(9, bankNumber);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating claim failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getString(1);
                }
                else {
                    throw new SQLException("Creating claim failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    //List of documents
    public void updateFileNameComboBox(String newFileName) {
        fileNameComboBox.getItems().add(newFileName);
    }

    @FXML
    public void openUploadWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/Function/ProofUploadScene.fxml"));
        UploadController uploadController = new UploadController();
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
}