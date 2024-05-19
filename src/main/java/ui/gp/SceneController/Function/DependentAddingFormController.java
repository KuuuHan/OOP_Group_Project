package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;
import ui.gp.Models.Users.User;
import ui.gp.Tab.ErrorMessageController;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DependentAddingFormController
{
    public TextField idFieldDependent;
    public TextField fullnameFieldDependent;
    public TextField usernameFieldDependent;
    public TextField passwordFieldDependent;
    public TextField emailFieldDependent;
    public TextField phonenumberFieldDependent;
    public TextField addressFieldDependent;
    public ComboBox<String> policyHolderChoiceBoxDependent;
    public Button submitButtonAddDependent;
    private String policyHolderID;
    private DatabaseConnection databaseConnection;
    private String policyOwnerID;

    @FXML
    public void initialize(){
        Session session = Session.getInstance();
        User user = session.getUser();
        String policyOwnerID = user.getId();
        loadPolicyHolderIDs(policyOwnerID);
    }

    private void loadPolicyHolderIDs(String policyOwnerID) {
        new Thread(() -> {
            List<String> policyHolderIDs = getPolicyHolderIDsLinkedWithOwner(policyOwnerID);
            Platform.runLater(() -> {
                ObservableList<String> policyHolderIDsObservable = FXCollections.observableArrayList(policyHolderIDs);
                policyHolderChoiceBoxDependent.setItems(policyHolderIDsObservable);
            });
        }).start();
    }

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void handleDependentSubmitButton(ActionEvent event)
    {
        String fullname = fullnameFieldDependent.getText();
        String username = usernameFieldDependent.getText();
        String password = passwordFieldDependent.getText();
        String email = emailFieldDependent.getText();
        String phoneNumber = phonenumberFieldDependent.getText();
        String address = addressFieldDependent.getText();
        String policyHolder = policyHolderChoiceBoxDependent.getValue();

// Check if all fields are filled out
        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            showErrorDialog("All fields must be filled out.");
            return;
        }

        // Check if the email ends with "@gmail.com"
        if (!email.endsWith("@gmail.com")) {
            showErrorDialog("Email must end with '@gmail.com'.");
            return;
        }

        // Check if the phone number has exactly 10 digits
        if (phoneNumber.length() != 10) {
            showErrorDialog("Phone number must have exactly 10 digits.");
            return;
        }

        new Thread(() -> {
            String dependentID = addDependent(fullname, username, password, email, phoneNumber, address, policyHolder);
            addPolicyHolder(dependentID, policyHolderID);
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddDependent.getScene().getWindow();
                stage.close();
            });
        }).start();
    }

    private void showErrorDialog(String errorMessage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gp/Scene/ErrorHandling/ErrorMessage.fxml"));
            Parent parent = fxmlLoader.load();
            ErrorMessageController dialogController = fxmlLoader.getController();
            dialogController.setErrorMessage(errorMessage);
            Scene scene = new Scene(parent, 300, 200);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String addDependent(String fullname, String username, String password, String email, String phoneNumber, String address, String policyHolder)
    {
        String id = null;
        try {
            String query = "INSERT INTO Users (fullname, username, password, email, phoneNumber, address, role) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
//            String policyHolderID = policyHolderChoiceBoxDependent.getSelectionModel().getSelectedItem();
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);

            statement.setString(1, fullname);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setString(6, address);
            statement.setString(7, String.valueOf(Role.Dependent));

            statement.executeUpdate();
            Statement idStatement = DatabaseConnection.getInstance().getConnection().createStatement();
            ResultSet rs = idStatement.executeQuery("SELECT id FROM Users WHERE username = '" + username + "'");
            if (rs.next()) {
                id = rs.getString(1);
                System.out.println(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void addPolicyHolder(String dependentID, String policyHolderID) {
        try {
            String query = "INSERT INTO policyholder (dependentid, policyholderid) VALUES ( ?, ?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);

            statement.setString(1, dependentID);
            statement.setString(2, policyHolderID); // cho nay la id cua policyholder

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            {
            }
        }
    }

    public List<String> getPolicyHolderIDsLinkedWithOwner(String policyOwnerID) {
        List<String> policyHolderIDs = new ArrayList<>();
        try {
//            String query = "SELECT Users.id FROM Users JOIN policyHolder ON Users.id = policyHolder.id WHERE policyOwner.id = ?";
            String query = "SELECT policyholderid FROM policyowner WHERE policyownerid = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, policyOwnerID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                policyHolderIDs.add(rs.getString("policyholderid"));
                System.out.println(rs.getString("policyholderid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policyHolderIDs;
    }

    public void policyHolderComboBox(ActionEvent event) {
//        String policyHolder = policyHolderChoiceBoxDependent.getSelectionModel().getSelectedItem();
//        List<String> policyHolderIDs = getPolicyHolderIDsLinkedWithOwner(policyOwnerID);
//
//        for (String id : policyHolderIDs) {
//            if (policyHolder.equals(id)) {
//                policyHolderID = id;
//                break;
//            }
//        }
        policyHolderID = policyHolderChoiceBoxDependent.getSelectionModel().getSelectedItem();
    }
}

