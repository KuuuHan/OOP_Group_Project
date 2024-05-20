package ui.gp.SceneController.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class DependentShowingController {
    public TextField IDFieldDependent;
    public TextField fullnameFieldDependent;
    public TextField usernameFieldDependent;
    public TextField passwordFieldDependent;
    public TextField emailFieldDependent;
    public TextField addressFieldDependent;
    public TextField phonenumberFieldDependent;
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
    
}
