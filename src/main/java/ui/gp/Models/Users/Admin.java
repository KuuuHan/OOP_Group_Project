package ui.gp.Models.Users;

import ui.gp.Models.Claim;
import ui.gp.Models.Role;

public class Admin extends User {
    public Admin(String id, String username, String password, Role role,
                 String fullName, String email, String phoneNumber, String address)
    {
        super(id, username, password, Role.System_Admin, fullName, email, phoneNumber, address);
    }

    public void createEntity(Object entity){

    }

    public void updateEntity(String entityID, Object entity){

    }

    public void deleteEntity(String entityID){

    }

    public Object retrieveEntity(String entityID) { return null; }

    public Claim retrieveClaimInfo(String claimID){

        return null;
    }

    public double sumSuccessClaimAmount() { return 0; }
}
