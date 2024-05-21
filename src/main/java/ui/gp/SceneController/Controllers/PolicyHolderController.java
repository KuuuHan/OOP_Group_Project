package ui.gp.SceneController.Controllers;

import javafx.util.Pair;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.InsuranceCard;
import ui.gp.Models.ReceiverBankingInfo;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyHolder;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Function.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyHolderController {

    private PolicyHolder policyHolder;
    private Connection connection;

    public PolicyHolderController(PolicyHolder policyHolder, Connection connection) {
        this.policyHolder = policyHolder;
        this.connection = connection;
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

    public List<Customer> retrieveBeneficiaries() {
        List<Customer> beneficiaries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE role IN ('Dependent');";
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


    public List<ReceiverBankingInfo> retrieveBank() {
        List<ReceiverBankingInfo> banks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ReceiverBankingInfo bank = new ReceiverBankingInfo(
                        resultSet.getString("bank_name"),
                        resultSet.getString("card_owner_bank"),
                        resultSet.getString("card_number_bank")
                );
                banks.add(bank);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banks;
    }

    public List<Pair<String, String>> retrieveHistory() {
        List<Pair<String, String>> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM historyrecord WHERE userid = '" + policyHolder.getId() + "'";
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
}
