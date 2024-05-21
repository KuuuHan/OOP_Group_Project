package ui.gp.SceneController.Controllers;

import javafx.util.Pair;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Function.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolicyOwnerController {
    private PolicyOwner policyOwner;
    private Connection connection;

    public PolicyOwnerController(PolicyOwner policyOwner, Connection connection) {
        this.policyOwner = policyOwner;
        this.connection = connection;
    }

    public String retrieveInformation() {
        return "ID: " + policyOwner.getId() + "\n" +
                "Full Name: " + policyOwner.getFullname() + "\n" +
                "Username: " + policyOwner.getUsername() + "\n" +
                "Password: " + policyOwner.getPassword() + "\n" +
                "Email: " + policyOwner.getEmail() + "\n" +
                "Phone Number: " + policyOwner.getPhonenumber() + "\n" +
                "Address: " + policyOwner.getAddress();
    }




    public List<Customer> retrieveBeneficiaries() {
        List<Customer> beneficiaries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE id IN " +
                    "(SELECT policyholderid FROM policyowner WHERE policyownerid = '" + policyOwner.getId() + "') " +
                    "UNION " +
                    "SELECT * FROM users WHERE id IN " +
                    "(SELECT dependentid FROM policyholder WHERE policyholderid IN " +
                    "(SELECT policyholderid FROM policyowner WHERE policyownerid = '" + policyOwner.getId() + "'))";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                Customer beneficiary = new Customer(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role,
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("address")
                );
                beneficiaries.add(beneficiary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beneficiaries;
    }
    public List<Claim> retrieveAllClaims() {
        List<Claim> claimList = new ArrayList<>();
        try {
            User currentUser = Session.getInstance().getUser();
            String currentUsername = currentUser.getUsername();
            String currentPassword = currentUser.getPassword();

            String query = "SELECT * FROM claim JOIN Users ON claim.policy_owner_insurance = Users.id WHERE Users.username = ? AND Users.password = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, currentUsername);
            statement.setString(2, currentPassword);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Claim claims = new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number_insurance"),
                        rs.getDate("exam_date"),
                        rs.getDouble("claim_amount"),
                        rs.getString("claim_status"),
                        rs.getString("card_number_bank"),
                        rs.getString("bank_name"),
                        rs.getString("card_owner_bank"),
                        rs.getDate("expiration_date_insurance"),
                        rs.getString("policy_owner_insurance"),
                        rs.getString("card_holder_insurance")
                );
                claimList.add(claims);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimList;
    }

    public static Claim retrieveclaimsforid(String Claimid) {

        try {
            String query = "SELECT * FROM claim WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, Claimid); // Set the Claimid to the prepared statement
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Claim claims = new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number_insurance"),
                        rs.getDate("exam_date"),
                        rs.getDouble("claim_amount"),
                        rs.getString("claim_status"),
                        rs.getString("card_number_bank"),
                        rs.getString("bank_name"),
                        rs.getString("card_owner_bank"),
                        rs.getDate("expiration_date_insurance"),
                        rs.getString("policy_owner_insurance"),
                        rs.getString("card_holder_insurance")
                );
                return claims;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Pair<String, String>> retrieveHistory() {
        List<Pair<String, String>> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM historyrecord WHERE userid = '" + policyOwner.getId() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String userid = resultSet.getString("userid");
                String action = resultSet.getString("action");
                data.add(new Pair<>(userid, action));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
