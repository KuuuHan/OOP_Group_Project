package ui.gp.SceneController.Controllers;

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

    public List<Claim> retrieveRejectedClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Rejected');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("claim_status"));
                Claim claim = new Claim(
                        resultSet.getString("id"),
                        resultSet.getDate("claim_date"),
                        resultSet.getString("insured_person"),
                        resultSet.getDate("exam_date"),
                        resultSet.getDouble("claim_amount"),
                        status,
                        resultSet.getString("card_number_bank")
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Claim> retrieveApprovedClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Approved');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("claim_status"));
                Claim claim = new Claim(
                        resultSet.getString("id"),
                        resultSet.getDate("claim_date"),
                        resultSet.getString("insured_person"),
                        resultSet.getDate("exam_date"),
                        resultSet.getDouble("claim_amount"),
                        status,
                        resultSet.getString("card_number_bank")
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Claim> retrieveClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("claim_status"));
                Claim claim = new Claim(
                        resultSet.getString("id"),
                        resultSet.getDate("claim_date"),
                        resultSet.getString("insured_person"),
                        resultSet.getDate("exam_date"),
                        resultSet.getDouble("claim_amount"),
                        status,
                        resultSet.getString("card_number_bank")
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Claim> retrievePendingClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Pending');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("claim_status"));
                Claim claim = new Claim(
                        resultSet.getString("id"),
                        resultSet.getDate("claim_date"),
                        resultSet.getString("insured_person"),
                        resultSet.getDate("exam_date"),
                        resultSet.getDouble("claim_amount"),
                        status,
                        resultSet.getString("card_number_bank")
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
}
