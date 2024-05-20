package ui.gp.SceneController.Controllers;

import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Role;
import ui.gp.Models.Users.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyorController {
    private final InsuranceSurveyor surveyor;
    private Connection connection;

    public SurveyorController(InsuranceSurveyor surveyor, Connection connection) {
        this.surveyor = surveyor;
        this.connection = connection;
    }

    public String retrieveInformation() {
        return "ID: " + surveyor.getId() + "\n" +
                "Full Name: " + surveyor.getFullname() + "\n" +
                "Username: " + surveyor.getUsername() + "\n" +
                "Password: " + surveyor.getPassword() + "\n" +
                "Email: " + surveyor.getEmail() + "\n" +
                "Phone Number: " + surveyor.getPhonenumber() + "\n" +
                "Address: " + surveyor.getAddress();
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

    public List<Claim> retrieveRejectedClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Rejected');";
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

    public List<Claim> retrieveApprovedClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Approved');";
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

    public List<Claim> retrievePendingClaims() {
        List<Claim> claims = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM claim WHERE claim_status IN ('Pending');";
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

}
