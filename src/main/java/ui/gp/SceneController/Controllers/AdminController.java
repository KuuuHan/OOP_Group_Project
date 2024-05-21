package ui.gp.SceneController.Controllers;

import javafx.util.Pair;
import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Role;
import ui.gp.Models.Users.SystemAdmin;
import ui.gp.Models.Users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private final SystemAdmin systemAdmin;
    private Connection connection;

    public AdminController(SystemAdmin systemAdmin, Connection connection) {
        this.systemAdmin = systemAdmin;
        this.connection = connection;
    }

    public String retrieveInformation() {
        return "ID: " + systemAdmin.getId() + "\n" +
                "Full Name: " + systemAdmin.getFullname() + "\n" +
                "Username: " + systemAdmin.getUsername() + "\n" +
                "Password: " + systemAdmin.getPassword() + "\n" +
                "Email: " + systemAdmin.getEmail() + "\n" +
                "Phone Number: " + systemAdmin.getPhonenumber() + "\n" +
                "Address: " + systemAdmin.getAddress();
    }

    public List<User> retrieveSystemAccount() {
        List<User> systemAccounts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role,
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("address")
                );
                systemAccounts.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return systemAccounts;
    }


    public List<Claim> retrieveClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ClaimStatus status = ClaimStatus.valueOf(rs.getString("claim_status"));
                Claim claim = new Claim(
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
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Pair<String, String>> retrieveHistory() {
        List<Pair<String, String>> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM historyrecord WHERE userid = '" + systemAdmin.getId() + "'";
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
