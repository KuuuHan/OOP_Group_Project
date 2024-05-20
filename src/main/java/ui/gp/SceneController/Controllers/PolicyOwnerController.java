package ui.gp.SceneController.Controllers;

import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Claim> retrieveAllClaims() {
        List<Claim> claims = new ArrayList<>();
        try {


            String query = "SELECT * FROM claim WHERE policy_owner_insurance = ?";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, policyOwner.getId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Claim claim = new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("exam_date"),
                        rs.getDate("claim_amount"),
                        rs.getDouble("claim_status"),
                        rs.getString("card_number_bank"),
                        rs.getString("bank_name"),
                        rs.getString("card_owner_bank"),
                        rs.getString("card_number_insurance"),
                        rs.getDate("expiration_date_insurance"),
                        rs.getString("policy_owner_insurance"),
                        rs.getString("card_holder_insurance")
                );
                claims.add(claim);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
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
    public void deleteBeneficiary(String id) {
        try {
            // Delete the beneficiary from the local database
            String query = "DELETE FROM Users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Beneficiary with ID: " + id + " deleted successfully from the local database.");
            } else {
                System.out.println("No beneficiary found with ID: " + id + " in the local database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
