package ui.gp.SceneController.Controllers;

import ui.gp.Models.InsuranceCard;
import ui.gp.Models.ReceiverBankingInfo;
import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyHolder;

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

    public List<InsuranceCard> retrieveInsurance() {
        List<InsuranceCard> cards = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                InsuranceCard card = new InsuranceCard(
                        resultSet.getString("card_number_insurance"),
                        resultSet.getString("card_holder_insurance"),
                        resultSet.getString("policy_owner_insurance"),
                        resultSet.getDate("expiration_date_insurance")

                );
                cards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
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
}
