package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.User;
import ui.gp.Tab.ErrorMessageController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DependentUpdatingFormController {
    public TextField fullnameFieldDependent;
    public TextField usernameFieldDependent;
    public TextField passwordFieldDependent;
    public TextField phonenumberFieldDependent;
    public TextField emailFieldDependent;
    public TextField addressFieldDependent;
    public Button submitButtonAddDependent;
    public TextField IDFieldDependent;
    public TextField policyHolderField;
    private DatabaseConnection databaseConnection;
    private User user;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void initialise()
    {
        String[] information = retrieveInformation().split("\n");
        IDFieldDependent.setText(information[0].split(": ")[1]);
        fullnameFieldDependent.setText(information[1].split(": ")[1]);
        usernameFieldDependent.setText(information[2].split(": ")[1]);
        passwordFieldDependent.setText(information[3].split(": ")[1]);
        emailFieldDependent.setText(information[4].split(": ")[1]);
        phonenumberFieldDependent.setText(information[5].split(": ")[1]);
        addressFieldDependent.setText(information[6].split(": ")[1]);
        try {
            String dependentID = information[0].split(": ")[1];
            String query = "SELECT policyholder.policyholderid, users.fullname FROM policyholder JOIN users ON policyholder.policyholderid = users.id WHERE policyholder.dependentid = ?";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);
            statement.setString(1,dependentID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String policyholderId = resultSet.getString("policyholderid");
                String fullname = resultSet.getString("fullname");
                policyHolderField.setText(policyholderId + " - " + fullname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String retrieveInformation() {
        return "ID: " + user.getId() + "\n" +
                "Full Name: " + user.getFullname() + "\n" +
                "Username: " + user.getUsername() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Phone Number: " + user.getPhonenumber() + "\n" +
                "Address: " + user.getAddress();
    }

    public void handleDependentSubmitButton(ActionEvent event)
    {
        String username = usernameFieldDependent.getText();
        String password = passwordFieldDependent.getText();
        String email = emailFieldDependent.getText();
        String phoneNumber = phonenumberFieldDependent.getText();
        String address = addressFieldDependent.getText();

// Check if all fields are filled out
        if (password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
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
            updateDependent(username, password, email, phoneNumber, address);
            recordHistory(user.getId(),"Update info of dependent");
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddDependent.getScene().getWindow();
                stage.close();
            });
        }).start();
    }

    private void recordHistory(String userId, String action) {
        try {
            String query = "INSERT INTO historyrecord (userid, action) VALUES (?, ?)";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, action);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateDependent(String username,String password, String email, String phoneNumber, String address)
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
}
