package ui.gp.SceneController.Controllers;

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
}
