package ui.gp.SceneController.Controllers;

import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Function.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PolicyOwnerController {
    private final PolicyOwner policyOwner;
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
            String query = "SELECT * FROM claim WHERE id = Claimid";
            PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

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


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
//    public Claim(String id,
//                 java.util.Date date,
//                 String insuredPersonID,
//                 String cardNumber,
//                 java.util.Date examDate,
//                 double claimAmount,
//                 String status
//                 , String
//                         receiverBankingNumber,
//                 String receiverBankingName,
//                 String receiverHolderName,
//                 Date Expdate,
//                 String policy_owner_insuranceid,
//                 String InsuranceCardHolderName)

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
