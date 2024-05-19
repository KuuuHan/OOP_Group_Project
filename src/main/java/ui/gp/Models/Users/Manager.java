package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public class Manager extends Provider
{
    public Manager(String id, String username, String password, Role role,
                   String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Insurance_Manager, fullname, email, phonenumber, address);
    }

    public void approveClaim(Claim claim)
    {

    }

    public void rejectClaim(Claim claim)
    {

    }

    public List<Claim> retrieveAllClaims()
    {
        return null;
    }

    public List<Customer> retrieveCustomers()
    {
        return null;
    }

    public InsuranceSurveyor getSurveyor(String surveyorId)
    {
        return null;
    }
}
