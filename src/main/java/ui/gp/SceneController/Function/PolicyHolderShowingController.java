package ui.gp.SceneController.Function;

import javafx.scene.control.TextField;
import ui.gp.Database.DatabaseConnection;
import ui.gp.Models.Users.Customer;
import ui.gp.Models.Users.User;

public class PolicyHolderShowingController {
    public TextField fullnameFieldAddPolicyHolder;
    public TextField usernameFieldAddPolicyHolder;
    public TextField passwordFieldAddPolicyHolder;
    public TextField emailFieldAddPolicyHolder;
    public TextField phonenumberFieldAddPolicyHolder;
    public TextField addressFieldAddPolicyHolder;
    public TextField IDFieldAddPolicyHolder;
    private DatabaseConnection databaseConnection;
    private User user;

    public void setDatabaseConnection(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void initialise()
    {
        String[] information = retrieveInformation().split("\n");
        IDFieldAddPolicyHolder.setText(information[0].split(": ")[1]);
        fullnameFieldAddPolicyHolder.setText(information[1].split(": ")[1]);
        usernameFieldAddPolicyHolder.setText(information[2].split(": ")[1]);
        passwordFieldAddPolicyHolder.setText(information[3].split(": ")[1]);
        emailFieldAddPolicyHolder.setText(information[4].split(": ")[1]);
        phonenumberFieldAddPolicyHolder.setText(information[5].split(": ")[1]);
        addressFieldAddPolicyHolder.setText(information[6].split(": ")[1]);
    }
    private String retrieveInformation() {
        return "ID: " + user.getId() + "\n" +
                "Full Name: " + user.getFullname() + "\n" +
                "Username: " + user.getUsername() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Phone Number: " + user.getPhonenumber() + "\n" +
                "Address: " + user.getAddress();
    }
}
