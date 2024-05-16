package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public class Dependent extends Customer
{
    private PolicyHolder policyHolder;
    private PolicyOwner policyOwner;

    public Dependent(String id, String username, String password, Role role,
                     String fullname, String email, String phonenumber, String address,
                     InsuranceCard insuranceCard, List<Claim> claims,
                     PolicyHolder policyHolder, PolicyOwner policyOwner)
    {
        super(id, username, password, Role.Dependent, fullname, email, phonenumber, address, insuranceCard, claims);
        this.policyHolder = policyHolder;
        this.policyOwner = policyOwner;
    }

    public Claim retrieveClaim(String claimId)
    {
        return null;
    }

    public String getInformation()
    {
       return null;
    }

}
