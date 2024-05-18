package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Policy.OwnerHomeController;
import ui.gp.View.ViewFactory;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            String policyHolderID =  addPolicyHolder(fullname, username, password, email, phoneNumber, address);
            Session session = Session.getInstance();
            User user = session.getUser();
            String policyOwnerID = user.getId();
            addPolicyOwner(policyOwnerID,policyHolderID);
            Platform.runLater(() -> {
                Stage stage = (Stage) submitButtonAddPolicyOwner.getScene().getWindow();
                stage.close();
            });
        }).start();
    }


    public String addPolicyHolder(String fullname, String username, String password, String email, String phoneNumber, String address) {
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
            statement.setString(7, String.valueOf(Role.Policy_Holder));

            statement.executeUpdate();
            Statement idStatement = databaseConnection.getConnection().createStatement();
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

    public void addPolicyOwner(String policyOwnerID, String policyHolderID) {
        try {
            String query = "INSERT INTO policyowner (policyholderid, policyownerid) VALUES ( ?, ?)";
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(query);

            statement.setString(1, policyHolderID);
            statement.setString(2, policyOwnerID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            {
            }
        }
    }
}
