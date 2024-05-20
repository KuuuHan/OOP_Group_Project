package ui.gp.SceneController.Controllers;

import ui.gp.Models.Role;
import ui.gp.Models.Users.Surveyor;
import ui.gp.Models.Users.SystemAdmin;
import ui.gp.Models.Users.User;

import java.sql.Connection;

public class SurveyorController {
    private final Surveyor surveyor;
    private Connection connection;

    public SurveyorController(Surveyor surveyor, Connection connection) {
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
}
