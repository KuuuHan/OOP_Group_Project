package ui.gp.SceneController.Controllers;

import ui.gp.Models.Users.PolicyOwner;

public class PolicyOwnerController {
    private PolicyOwner policyOwner;
    public PolicyOwnerController(PolicyOwner policyOwner)
    {
        this.policyOwner = policyOwner;
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
}
