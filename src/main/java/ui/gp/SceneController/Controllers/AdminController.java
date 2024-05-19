package ui.gp.SceneController.Controllers;

import ui.gp.Models.Users.SystemAdmin;

import java.sql.Connection;

public class AdminController {
    private final SystemAdmin systemAdmin;

    public AdminController(SystemAdmin systemAdmin, Connection connection) {
        this.systemAdmin = systemAdmin;
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
}
