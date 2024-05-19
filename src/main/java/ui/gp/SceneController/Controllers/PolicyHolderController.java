package ui.gp.SceneController.Controllers;

import ui.gp.Models.Role;
import ui.gp.Models.Users.PolicyHolder;
import ui.gp.Models.Users.PolicyOwner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PolicyHolderController {

    private PolicyHolder policyHolder;
    private Connection connection;

    public PolicyHolderController(PolicyHolder policyHolder, Connection connection) {
        this.policyHolder = policyHolder;
        this.connection = connection;
    }
    public PolicyHolder getPolicyHolderByID(String holderID) {
        PolicyHolder policyHolder = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM policyholders WHERE id = '" + holderID + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                // Assuming a constructor for PolicyHolder class that accepts necessary parameters
                Role role = Role.valueOf(resultSet.getString("role"));
                policyHolder = new PolicyHolder(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role,
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return policyHolder;
    }

    public List<String> retrieveHolderIDs() {
        List<String> holderIDs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT holderID FROM claims WHERE status IN ('Depending');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String holderID = resultSet.getString("holderID");
                holderIDs.add(holderID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holderIDs;
    }

    public String retrieveInformation() {
        return "ID: " + policyHolder.getId() + "\n" +
                "Full Name: " + policyHolder.getFullname() + "\n" +
                "Username: " + policyHolder.getUsername() + "\n" +
                "Password: " + policyHolder.getPassword() + "\n" +
                "Email: " + policyHolder.getEmail() + "\n" +
                "Phone Number: " + policyHolder.getPhonenumber() + "\n" +
                "Address: " + policyHolder.getAddress();
    }
}
