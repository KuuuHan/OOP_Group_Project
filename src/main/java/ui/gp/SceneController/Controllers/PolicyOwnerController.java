package ui.gp.SceneController.Controllers;

import ui.gp.Models.Role;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.PolicyOwner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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


}
