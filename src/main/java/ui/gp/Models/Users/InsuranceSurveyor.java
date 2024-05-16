package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public class InsuranceSurveyor extends Provider
{
    public InsuranceSurveyor(String id, String username, String password, Role role,
                             String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Insurance_Surveyor, fullname, email, phonenumber, address);
    }

    public void requireMoreInformation(Claim claim)
    {

    }

    public void proposeClaimToManager(Claim claim, InsuranceManager manager)
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
}
