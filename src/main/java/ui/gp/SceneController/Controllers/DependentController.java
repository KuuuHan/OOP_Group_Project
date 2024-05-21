package ui.gp.SceneController.Controllers;

import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Claim;
import ui.gp.Models.ClaimStatus;
import ui.gp.Models.Users.Dependent;
import ui.gp.Models.Users.User;
import ui.gp.SceneController.Function.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DependentController
{
    private Dependent dependent;
    private Connection connection;

    public DependentController(Dependent dependent, Connection connection)
    {

        this.dependent = dependent;
        this.connection = connection;
    }




    public String retrieveInformation() {
        return "ID: " + dependent.getId() + "\n" +
                "Full Name: " + dependent.getFullname() + "\n" +
                "Username: " + dependent.getUsername() + "\n" +
                "Password: " + dependent.getPassword() + "\n" +
                "Email: " + dependent.getEmail() + "\n" +
                "Phone Number: " + dependent.getPhonenumber() + "\n" +
                "Address: " + dependent.getAddress();
    }

    public List<Claim> retrieveClaims() {
        List<Claim> claimList = new ArrayList<>();
        try {
            User currentUser = Session.getInstance().getUser();
            String currentUsername = currentUser.getUsername();
            String currentPassword = currentUser.getPassword();

            String query = "SELECT * FROM claim JOIN Users ON claim.insured_person = Users.id WHERE Users.username = ? AND Users.password = ?";
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
