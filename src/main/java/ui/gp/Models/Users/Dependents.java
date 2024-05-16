package ui.gp.Models.Users;

import ui.gp.Models.*;

import java.util.List;

public class Dependents extends Customer {
    private PolicyOwner policyOwner;
    private PolicyHolder policyHolder;
    public Dependents(String id, String username, String password,
                      Role role, String fullname, String email,
                      String phonenumber, String address,
                      InsuranceCard insuranceCard, List<Claim> claims,
                      PolicyOwner policyOwner, PolicyHolder policyHolder)
    {
        super(id, username, password, role, fullname, email, phonenumber,
                address, insuranceCard, claims);
        this.policyOwner = policyOwner;
        this.policyHolder = policyHolder;
    }

    public Claim retrieveClaim(String claimId) { return null; }

    public String getInformation() { return null; }
}
