package ui.gp.Models.Users;

import ui.gp.Models.*;

public class SystemAdmin extends User
{
    public SystemAdmin(String id, String username, String password, Role role,
                       String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.System_Admin, fullname, email, phonenumber, address);
    }

    public void createEntity(Object entity)
    {

    }

    public void updateEntity(String entityId, Object entity)
    {

    }

    public void deleteEntity(String entityId)
    {

    }

    public Object retrieveEntity(String entityId)
    {
        return null;
    }

    public Claim retrieveClaimInformation(String claimId)
    {

        return null;
    }

    public double sumSuccessfullyClaimedAmount()
    {
        return 0;
    }
}
