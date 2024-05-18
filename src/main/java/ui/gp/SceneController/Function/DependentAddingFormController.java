package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DependentAddingFormController
{
    public TextField idFieldDependent;
    public TextField fullnameFieldDependent;
    public TextField usernameFieldDependent;
    public TextField passwordFieldDependent;
    public TextField emailFieldDependent;
    public TextField phonenumberFieldDependent;
    public TextField addressFieldDependent;
    public ChoiceBox policyHolderBoxDependent;
    public Button submitButtonAddDependent;
    private DatabaseConnection databaseConnection;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
    public void handleSubmitButton(ActionEvent event)
    {
        String fullname = fullnameFieldDependent.getText();
        String username = usernameFieldDependent.getText();
        String password = passwordFieldDependent.getText();
        String email = emailFieldDependent.getText();
        String phoneNumber = phonenumberFieldDependent.getText();
        String address = addressFieldDependent.getText();
        String policyHolder = policyHolderBoxDependent.getValue().toString();

        new Thread(() -> {
            addDependent(fullname, username, password, email, phoneNumber, address, policyHolder);
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddDependent.getScene().getWindow();
                stage.close();
            });
        }).start();
    }
    private void addDependent(String fullname, String username, String password, String email, String phoneNumber, String address, String policyHolder)
    {
        try {
            String query = "INSERT INTO Users (fullname, username, password, email, phoneNumber, address,role) VALUES ( ? ?, ?, ?, ?, ?,?)";
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
