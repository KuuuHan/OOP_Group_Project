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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;
import ui.gp.Models.Users.PolicyHolder;
import ui.gp.Models.Users.User;
import ui.gp.Tab.ErrorMessageController;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class DependentAddingFormController
{
    public TextField idFieldDependent;
    public TextField fullnameFieldDependent;
    public TextField usernameFieldDependent;
    public TextField passwordFieldDependent;
    public TextField emailFieldDependent;
    public TextField phonenumberFieldDependent;
    public TextField addressFieldDependent;
    public ChoiceBox<String> policyHolderChoiceBoxDependent;
    public Button submitButtonAddDependent;
    private DatabaseConnection databaseConnection;
    private List<PolicyHolder> policyholders;
    private Map<String,String> policyHolderMap;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void setPolicyHoldersList(List<PolicyHolder> policyholders)
    {
        ObservableList<String> policyHolderNames = FXCollections.observableArrayList();
        for (PolicyHolder policyHolder : policyholders) {
            policyHolderNames.add(policyHolder.getId() + " - " + policyHolder.getFullname());
        }
        policyHolderChoiceBoxDependent.setItems(policyHolderNames);
    }

    public void handleDependentSubmitButton(ActionEvent event)
    {
        String fullname = fullnameFieldDependent.getText();
        String username = usernameFieldDependent.getText();
        String password = passwordFieldDependent.getText();
        String email = emailFieldDependent.getText();
        String phoneNumber = phonenumberFieldDependent.getText();
        String address = addressFieldDependent.getText();
        String policyHolde = policyHolderChoiceBoxDependent.getValue();
        String policyHolderID = policyHolde.split(" - ")[0];

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
            String dependentID = addDependent(fullname, username, password, email, phoneNumber, address);
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

    private String addDependent(String fullname, String username, String password, String email, String phoneNumber, String address)
    {
        String id = null;
        try {
            String query = "INSERT INTO Users (fullname, username, password, email, phoneNumber, address,role) VALUES ( ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);

            statement.setString(1, fullname);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setString(6, address);
            statement.setString(7, String.valueOf(Role.Dependent));

            statement.executeUpdate();
            Statement idStatement = databaseConnection.getConnection().createStatement();
            ResultSet rs = idStatement.executeQuery("SELECT id FROM Users WHERE username = '" + username + "'"); //Auto generated ID
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
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);

            statement.setString(1, dependentID);
            statement.setString(2, policyHolderID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            {
            }
        }
    }
}

