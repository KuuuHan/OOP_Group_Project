package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PolicyHolderAddingFormController {
    public TextField fullnameFieldAddPolicyHolder;
    public TextField usernameFieldAddPolicyHolder;
    public TextField passwordFieldAddPolicyHolder;
    public TextField emailFieldAddPolicyHolder;
    public TextField phonenumberFieldAddPolicyHolder;
    public TextField addressFieldAddPolicyHolder;
    public Button submitButtonAddPolicyOwner;
    private DatabaseConnection databaseConnection;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
    public void handleSubmitButton(ActionEvent event)
    {
        String fullname = fullnameFieldAddPolicyHolder.getText();
        String username = usernameFieldAddPolicyHolder.getText();
        String password = passwordFieldAddPolicyHolder.getText();
        String email = emailFieldAddPolicyHolder.getText();
        String phoneNumber = phonenumberFieldAddPolicyHolder.getText();
        String address = addressFieldAddPolicyHolder.getText();

        new Thread(() -> {
            addPolicyHolder(fullname, username, password, email, phoneNumber, address);
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddPolicyOwner.getScene().getWindow();
                stage.close();
            });
        }).start();
    }
    private void addPolicyHolder(String fullname, String username, String password, String email, String phoneNumber, String address)
    {
        try {
            String query = "INSERT INTO Users (fullname, username, password, email, phoneNumber, address,role) VALUES ( ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);

            statement.setString(1, fullname);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setString(6, address);
            statement.setString(7, String.valueOf(Role.Policy_Holder));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
