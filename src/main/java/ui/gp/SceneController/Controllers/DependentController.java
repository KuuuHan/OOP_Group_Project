package ui.gp.SceneController.Controllers;

import ui.gp.Models.Users.Dependent;

import java.sql.Connection;

public class DependentController
{
    private Dependent dependent;

    public DependentController(Dependent dependent, Connection connection)
    {
        this.dependent = dependent;
    }

//    public List<Claim> retrieveClaims()
//    {
//        return dependent.getClaims();
//    }

    public String retrieveInformation() {
        return "ID: " + dependent.getId() + "\n" +
                "Full Name: " + dependent.getFullname() + "\n" +
                "Username: " + dependent.getUsername() + "\n" +
                "Password: " + dependent.getPassword() + "\n" +
                "Email: " + dependent.getEmail() + "\n" +
                "Phone Number: " + dependent.getPhonenumber() + "\n" +
                "Address: " + dependent.getAddress();
    }

}
