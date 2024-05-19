package ui.gp.SceneController.Controllers;

import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Role;
import ui.gp.Models.Users.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    private Manager manager;
    private Connection connection;

    public ManagerController(Manager manager, Connection connection) {
        this.manager = manager;
        this.connection = connection;
    }

    public String retrieveInformation() {
        return "ID: " + manager.getId() + "\n" +
                "Full Name: " + manager.getFullname() + "\n" +
                "Username: " + manager.getUsername() + "\n" +
                "Password: " + manager.getPassword() + "\n" +
                "Email: " + manager.getEmail() + "\n" +
                "Phone Number: " + manager.getPhonenumber() + "\n" +
                "Address: " + manager.getAddress();
    }



    public List<Customer> retrieveBeneficiaries() {
        List<Customer> beneficiaries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE role IN ('Policy_Holder', 'Dependent');";
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

    public List<Customer> retrieveHolderBeneficiaries() {
        List<Customer> beneficiaries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE role IN ('Policy_Holder');";
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

    public List<Customer> retrieveDependentBeneficiaries() {
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

    public List<Claim> retrievePendingClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claims WHERE status IN ('Pending');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("status"));
                String holderId = resultSet.getString("holderId");
                Claim claim = new Claim(
                        resultSet.getString("claimId"),
                        resultSet.getDate("claimDate"),
                        null,
                        resultSet.getString("insuranceNumber"),
                        resultSet.getDate("examDate"),
                        null,
                        resultSet.getDouble("claimAmount"),
                        status,
                        null
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Claim> retrieveRejectedClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claims WHERE status IN ('Rejected');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("status"));
                String holderId = resultSet.getString("holderId");
                Claim claim = new Claim(
                        resultSet.getString("claimId"),
                        resultSet.getDate("claimDate"),
                        null,
                        resultSet.getString("insuranceNumber"),
                        resultSet.getDate("examDate"),
                        null,
                        resultSet.getDouble("claimAmount"),
                        status,
                        null
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
            String query = "SELECT * FROM claims WHERE status IN ('Approved');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("status"));
                String holderId = resultSet.getString("holderId");
                Claim claim = new Claim(
                        resultSet.getString("claimId"),
                        resultSet.getDate("claimDate"),
                        null,
                        resultSet.getString("insuranceNumber"),
                        resultSet.getDate("examDate"),
                        null,
                        resultSet.getDouble("claimAmount"),
                        status,
                        null
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
            String query = "SELECT * FROM claims;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClaimStatus status = ClaimStatus.valueOf(resultSet.getString("status"));
                String holderId = resultSet.getString("holderId");
                Claim claim = new Claim(
                        resultSet.getString("claimId"),
                        resultSet.getDate("claimDate"),
                        null,
                        resultSet.getString("insuranceNumber"),
                        resultSet.getDate("examDate"),
                        null,
                        resultSet.getDouble("claimAmount"),
                        status,
                        null
                );
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<Provider> retrieveSurveyor() {
        List<Provider> beneficiaries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE role IN ('Insurance_Surveyor');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                Provider beneficiary = new Provider(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role,
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("address")
                ) {
                };
                beneficiaries.add(beneficiary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beneficiaries;
    }


}
