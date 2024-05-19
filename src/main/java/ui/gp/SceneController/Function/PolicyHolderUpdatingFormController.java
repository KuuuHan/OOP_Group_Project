package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Controllers.PolicyOwnerController;
import ui.gp.Tab.ErrorMessageController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PolicyHolderUpdatingFormController {
    public TextField fullnameFieldAddPolicyHolder;
    public TextField usernameFieldAddPolicyHolder;
    public TextField passwordFieldAddPolicyHolder;
    public TextField emailFieldAddPolicyHolder;
    public TextField phonenumberFieldAddPolicyHolder;
    public TextField addressFieldAddPolicyHolder;
    public TextField IDFieldAddPolicyHolder1;
    public Button submitButtonAddPolicyOwner;
    private DatabaseConnection databaseConnection;
    private Customer user;
    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void setUser(Customer user)
    {
        this.user = user;
    }

    public void initialise()
    {
            String[] information = retrieveInformation().split("\n");
            IDFieldAddPolicyHolder1.setText(information[0].split(": ")[1]);
            fullnameFieldAddPolicyHolder.setText(information[1].split(": ")[1]);
            usernameFieldAddPolicyHolder.setText(information[2].split(": ")[1]);
            passwordFieldAddPolicyHolder.setText(information[3].split(": ")[1]);
            emailFieldAddPolicyHolder.setText(information[4].split(": ")[1]);
            phonenumberFieldAddPolicyHolder.setText(information[5].split(": ")[1]);
            addressFieldAddPolicyHolder.setText(information[6].split(": ")[1]);
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

    public void handleSubmitButton() {
        String password = passwordFieldAddPolicyHolder.getText();
        String email = emailFieldAddPolicyHolder.getText();
        String phoneNumber = phonenumberFieldAddPolicyHolder.getText();
        String address = addressFieldAddPolicyHolder.getText();
        String username = usernameFieldAddPolicyHolder.getText();

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
            updatePolicyHolder(password, email, phoneNumber, address,username);
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddPolicyOwner.getScene().getWindow();
                stage.close();
            });
        }).start();
    }

    private void updatePolicyHolder(String password, String email, String phoneNumber, String address,String username)
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
