package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer
{


    public PolicyHolder(String id, String username, String password, Role role, String fullname, String email, String phonenumber, String address)
    {
        super(id, username, password, Role.Policy_Holder, fullname, email, phonenumber, address);
    }

    public void fileClaim(Claim claim)
    {

    }

    public void updateClaim(Claim claim)
    {

    }

    public Claim retrieveClaim(String claimId)
    {
        return null;
    }

    public void fileDependentClaim(Dependent dependent, Claim claim)
    {

    }

    public void updateDependentClaim(Dependent dependent, Claim claim)
    {

    }

    public Claim retrieveDependentClaim(Dependent dependent, String claimId)
    {
        return null;
    }

    public void updateInformation(String phone, String address, String email, String password)
    {

    }

    public String getInformation()
    {
       return null;
    }

    public void updateDependentInformation(Dependent dependent, String phone, String address, String email, String password)
    {

    }

    public String getDependentInformation(Dependent dependent)
    {
        return null;
    }
}
